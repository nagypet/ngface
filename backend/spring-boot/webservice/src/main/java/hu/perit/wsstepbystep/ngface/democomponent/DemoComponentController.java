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

package hu.perit.wsstepbystep.ngface.democomponent;

import hu.perit.ngface.controller.ComponentController;
import hu.perit.ngface.widget.input.DateRangeInput;
import hu.perit.ngface.widget.input.Select;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Peter Nagy
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class DemoComponentController implements ComponentController<DemoComponentController.Params, DemoComponentData>
{
    @Data
    public static class Params
    {
        private final Long pageNumber;
        private final Long pageSize;
        private final String sortColumn;
        private final String sortDirection;
    }

    private final DemoTableDataProvider demoTableDataProvider;

    @Override
    public DemoComponentData initializeData(Params params)
    {
        DemoComponentData data = new DemoComponentData();
        data.setOwnersName("Peter");
        data.setRole("Admin");
        data.setAmount(new BigDecimal("2.123456789"));
        data.setCountSamples(new BigDecimal("10"));
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
        data.setTableRows(this.demoTableDataProvider.getTableRows(params.getPageNumber(), params.getPageSize(), params.getSortColumn(), params.getSortDirection()));
        data.setTotalTableRowCount(this.demoTableDataProvider.getLength());
        return data;
    }

    @Override
    public void onSave(DemoComponentData data)
    {
        log.debug(data.toString());
    }
}
