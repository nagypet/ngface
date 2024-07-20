/*
 * Copyright 2020-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package hu.perit.ngface.webservice.ngface.tabledetailscomponent;

import hu.perit.ngface.core.controller.ComponentController;
import hu.perit.ngface.core.widget.input.Autocomplete;
import hu.perit.ngface.webservice.db.addressdb.table.AddressEntity;
import hu.perit.ngface.webservice.exceptionhandler.ApplicationMessage;
import hu.perit.ngface.webservice.service.api.AddressService;
import hu.perit.spvitamin.core.exception.ApplicationRuntimeException;
import hu.perit.spvitamin.spring.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TableDetailsComponentController implements ComponentController<TableDetailsComponentDTO, Long>
{
    private final AddressService addressService;


    @Override
    public TableDetailsComponentDTO getForm(Long id)
    {
        if (id % 2 != 0)
        {
            throw new ApplicationRuntimeException(ApplicationMessage.ONLY_EVEN_IDS_CAN_BE_EDITED);
        }

        TableDetailsComponentDTO data = new TableDetailsComponentDTO();
        try
        {
            AddressEntity addressEntity = this.addressService.find(id);
            data.setId(String.valueOf(id));
            data.setName(addressEntity.getStreet());
            data.setPostCode(addressEntity.getPostCode());
            data.setCity(addressEntity.getCity());
            data.setStreet(addressEntity.getStreet());
            data.setDistrict(getAutocompleteData(addressEntity.getDistrict()));
            return data;
        }
        catch (ResourceNotFoundException e)
        {
            throw new RuntimeException(String.format("No data found with id: '%d'", id));
        }
    }


    private Autocomplete.Data getAutocompleteData(String value)
    {
        Autocomplete.Data data = new Autocomplete.Data(value);
        List<String> distinctDistricts = this.addressService.getDistinctDistricts(null);
        data.getExtendedReadOnlyData().options(distinctDistricts);
        return data;
    }


    @Override
    public void onFormSubmit(TableDetailsComponentDTO data)
    {
        this.addressService.update(Long.parseLong(data.getId()), data.getPostCode(), data.getCity(), data.getStreet(), data.getDistrict().getValue());
    }
}
