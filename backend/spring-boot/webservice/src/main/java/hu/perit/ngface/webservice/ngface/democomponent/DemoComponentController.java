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

package hu.perit.ngface.webservice.ngface.democomponent;

import hu.perit.ngface.controller.ComponentController;
import hu.perit.ngface.data.DataRetrievalParams;
import hu.perit.ngface.data.TableActionParams;
import hu.perit.ngface.widget.input.DateRangeInput;
import hu.perit.ngface.widget.input.Select;
import hu.perit.ngface.widget.table.Paginator;
import hu.perit.ngface.webservice.config.Constants;
import hu.perit.ngface.webservice.service.api.DemoTableDataProvider;
import hu.perit.ngface.webservice.service.api.Page;
import hu.perit.ngface.webservice.service.api.TableRowDTO;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author Peter Nagy
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class DemoComponentController implements ComponentController<DemoComponentController.Params, DemoComponentDTO>
{
    @Data
    public static class Params
    {
        private final DataRetrievalParams dataRetrievalParams;
        private final String rowId;
    }

    private final DemoTableDataProvider demoTableDataProvider;

    @Override
    public DemoComponentDTO initializeData(Params params)
    {
        if (StringUtils.isNotBlank(params.rowId))
        {
            return initializeDataForSingleTableRow(params.rowId);
        }

        return initializeDataFull(params.dataRetrievalParams);
    }


    private DemoComponentDTO initializeDataFull(DataRetrievalParams dataRetrievalParams)
    {
        // The data
        DemoComponentDTO data = new DemoComponentDTO();

        data.setOwnersName("Peter");
        data.setRole("Admin");
        data.setAmount(2.123456789);
        data.setCountSamples(10L);
        data.setCheckInDate(LocalDate.now());
        data.setDateRange(new DateRangeInput.Data(LocalDate.now(), LocalDate.now().plusDays(1)));
        data.setSelectData(new Select.Data()
                .addOption(new Select.Option("id_first", "First option"))
                .addOption(new Select.Option("id_second", "Second option")));
        data.setSelect2Data(new Select.Data()
                .addOption(new Select.Option("id_first", "First option"))
                .addOption(new Select.Option("id_second", "Second option")));
        data.setSelect3Data(new Select.Data()
                .addOption(new Select.Option("id_first", "First option"))
                .addOption(new Select.Option("id_second", "Second option")).selected("id_first"));

        Page<TableRowDTO> tableRows = this.demoTableDataProvider.getTableRows(dataRetrievalParams);
        data.getTableDTO().setRows(tableRows.getList());

        // Paginator
        data.getTableData().paginator(new Paginator(0, Constants.DEFAULT_PAGESIZE, tableRows.getTotalElements(), Arrays.asList(3, 5, 10, 20)));

        // Sorter

        // Filterer
        data.getTableData().addFilterer(this.demoTableDataProvider.getNameFilter());
        data.getTableData().addFilterer(this.demoTableDataProvider.getSymbolFilter());
        return data;
    }

    private DemoComponentDTO initializeDataForSingleTableRow(String rowId)
    {
        // The data
        DemoComponentDTO data = new DemoComponentDTO();

        Optional<TableRowDTO> optTableRow = this.demoTableDataProvider.getTableRowById(Long.parseLong(rowId));
        if (optTableRow.isPresent())
        {
            data.getTableDTO().setRows(List.of(optTableRow.get()));
        }

        // Paginator

        // Sorter

        // Filterer

        return data;
    }


    @Override
    public void onSave(DemoComponentDTO data)
    {
        // Here you can save the submitted data
    }

    @Override
    public void onActionClick(TableActionParams tableActionParams)
    {
        log.debug(tableActionParams.toString());
    }
}
