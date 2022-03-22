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

package hu.perit.wsstepbystep.ngface.tabledetailscomponent;

import hu.perit.ngface.controller.ComponentController;
import hu.perit.wsstepbystep.ngface.democomponent.DemoTableDataProvider;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TableDetailsComponentController implements ComponentController<TableDetailsComponentController.Params, TableDetailsComponentData>
{
    @Data
    public static class Params
    {
        private final Long id;
    }

    private final DemoTableDataProvider demoTableDataProvider;


    @Override
    public TableDetailsComponentData initializeData(Params params)
    {
        TableDetailsComponentData data = new TableDetailsComponentData();
        Optional<DemoTableDataProvider.DataRow> optTableRow = this.demoTableDataProvider.getTableRow(params.id);
        if (optTableRow.isPresent())
        {
            DemoTableDataProvider.DataRow dataRow = optTableRow.get();
            data.setId(String.valueOf(params.id));
            data.setName(dataRow.getName());
            data.setSymbol(dataRow.getSymbol());
            data.setWeight(dataRow.getWeight());
            return data;
        }

        throw new RuntimeException(String.format("No data found with id: %d", params.id));
    }


    @Override
    public void onSave(TableDetailsComponentData data)
    {
        Optional<DemoTableDataProvider.DataRow> optTableRow = this.demoTableDataProvider.getTableRow(Long.valueOf(data.getId()));
        if (optTableRow.isPresent())
        {
            DemoTableDataProvider.DataRow dataRow = optTableRow.get();
            dataRow.setWeight(data.getWeight());
            dataRow.setSymbol(data.getSymbol());
        }
    }
}
