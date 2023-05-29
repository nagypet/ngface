/*
 * Copyright 2020-2022 the original author or authors.
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

import hu.perit.ngface.controller.ComponentController;
import hu.perit.ngface.data.TableActionParams;
import hu.perit.ngface.webservice.service.api.DemoTableDataProvider;
import hu.perit.ngface.webservice.service.api.TableRowDTO;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TableDetailsComponentController implements ComponentController<TableDetailsComponentController.Params, TableDetailsComponentDTO>
{
    @Data
    public static class Params
    {
        private final Long id;
    }

    private final DemoTableDataProvider demoTableDataProvider;


    @Override
    public TableDetailsComponentDTO initializeData(Params params)
    {
        TableDetailsComponentDTO data = new TableDetailsComponentDTO();
        Optional<TableRowDTO> optTableRow = this.demoTableDataProvider.getTableRowById(params.id);
        if (optTableRow.isPresent())
        {
            TableRowDTO tableRowDTO = optTableRow.get();
            data.setId(String.valueOf(params.id));
            data.setName(tableRowDTO.getName());
            data.setSymbol(tableRowDTO.getSymbol());
            data.setWeight(tableRowDTO.getWeight());
            return data;
        }

        throw new RuntimeException(String.format("No data found with id: %d", params.id));
    }


    @Override
    public void onSave(TableDetailsComponentDTO data)
    {
        Optional<TableRowDTO> optTableRow = this.demoTableDataProvider.getTableRowById(Long.valueOf(data.getId()));
        if (optTableRow.isPresent())
        {
            TableRowDTO tableRowDTO = optTableRow.get();
            tableRowDTO.setWeight(data.getWeight());
            tableRowDTO.setSymbol(data.getSymbol());
        }
    }

    @Override
    public void onActionClick(TableActionParams tableActionParams)
    {

    }
}
