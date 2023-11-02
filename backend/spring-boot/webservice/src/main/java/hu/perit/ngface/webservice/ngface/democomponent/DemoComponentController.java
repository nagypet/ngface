/*
 * Copyright 2020-2023 the original author or authors.
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

package hu.perit.ngface.webservice.ngface.democomponent;

import hu.perit.ngface.core.controller.ComponentController;
import hu.perit.ngface.core.widget.input.Autocomplete;
import hu.perit.ngface.core.widget.input.DateRangeInput;
import hu.perit.ngface.core.widget.input.Select;
import hu.perit.ngface.webservice.service.api.AddressService;
import hu.perit.ngface.webservice.service.api.SessionData;
import hu.perit.ngface.webservice.service.api.SessionPersistenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Peter Nagy
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class DemoComponentController implements ComponentController<DemoComponentDTO, Long>
{
    private final SessionPersistenceService sessionPersistenceService;
    private final AddressService addressService;


    @Override
    public DemoComponentDTO getForm(Long id)
    {
        SessionData sessionData = this.sessionPersistenceService.getSessionData();
        DemoComponentDTO data = sessionData.getDemoComponentDTO();
        if (data != null)
        {
            data.setSelectData(getSelectData().selected(data.getSelectData().getSelected()));
            data.setSelect2Data(getSelectData().selected(data.getSelect2Data().getSelected()));
            data.setSelect3Data(getSelectData().selected(data.getSelect3Data().getSelected()));
            data.setAutocompleteData(getAutocompleteDistrictsData(data.getAutocompleteData().getValue()));
            data.setAutocompleteStreetsData(getAutocompleteStreetsData(data.getAutocompleteStreetsData().getValue()));

            return data;
        }

        // The data
        data = new DemoComponentDTO();

        data.setOwnersName("Peter");
        data.setRole("Admin");
        data.setPrice(2.123456789);
        data.setCountSamples(10L);
        data.setCheckInDate(LocalDate.now());
        data.setDateRange(new DateRangeInput.Data(LocalDate.now(), LocalDate.now().plusDays(1)));
        data.setSelectData(getSelectData());
        data.setSelect2Data(getSelectData());
        data.setSelect3Data(getSelectData().selected("id_first"));
        data.setAutocompleteData(getAutocompleteDistrictsData(null));
        data.setAutocompleteStreetsData(getAutocompleteStreetsData(null));

        return data;
    }


    private static Select.Data getSelectData()
    {
        return new Select.Data()
            .addOption(new Select.Option("id_first", "First option"))
            .addOption(new Select.Option("id_second", "Second option"))
            .addOption(new Select.Option("id_third", "Third option"))
            .addOption(new Select.Option("id_fourth", "Fourth option"))
            ;
    }


    private Autocomplete.Data getAutocompleteDistrictsData(String value)
    {
        Autocomplete.Data data = new Autocomplete.Data(value);
        List<String> distinctDistricts = this.addressService.getDistinctDistricts(null);
        data.getExtendedReadOnlyData().options(distinctDistricts);
        return data;
    }


    private Autocomplete.Data getAutocompleteStreetsData(String value)
    {
        Autocomplete.Data data = new Autocomplete.Data(value);
        List<String> distinctStreets = this.addressService.getDistinctStreets(null);
        data.getExtendedReadOnlyData().options(distinctStreets);
        return data;
    }


    @Override
    public void onFormSubmit(DemoComponentDTO data)
    {
        SessionData sessionData = this.sessionPersistenceService.getSessionData();
        sessionData.setDemoComponentDTO(data);
        this.sessionPersistenceService.saveSessionData(sessionData);
    }
}
