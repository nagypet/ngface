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
import hu.perit.ngface.core.data.DataRetrievalParams;
import hu.perit.ngface.core.data.TableActionParams;
import hu.perit.ngface.core.widget.input.DateRangeInput;
import hu.perit.ngface.core.widget.input.Select;
import hu.perit.ngface.core.widget.table.Filterer;
import hu.perit.ngface.core.widget.table.FiltererFactory;
import hu.perit.ngface.core.widget.table.Table;
import hu.perit.ngface.core.widget.table.TableDataBuilder;
import hu.perit.ngface.webservice.config.Constants;
import hu.perit.ngface.webservice.db.addressdb.table.AddressEntity;
import hu.perit.ngface.webservice.mapper.AddressMapper;
import hu.perit.ngface.webservice.model.AddressDTO;
import hu.perit.ngface.webservice.service.api.AddressService;
import hu.perit.spvitamin.spring.exception.ResourceNotFoundException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

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

    private final AddressService addressService;
    private final AddressMapper addressMapper;


    @Override
    public DemoComponentDTO initializeData(Params params)
    {
        if (StringUtils.isNotBlank(params.rowId))
        {
            return initializeDataForSingleTableRow(params.rowId);
        }

        return initializeDataFull(params);
    }


    private DemoComponentDTO initializeDataFull(Params params)
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

        // Table
        // Table: Data rows
        Page<AddressEntity> tableRows = this.addressService.find(DataRetrievalParams.applyDefaults(params.getDataRetrievalParams(), null));
        if (tableRows != null)
        {
            data.getTableDTO().setRows(this.addressMapper.map(tableRows.toList()));
        }

        // Table: Table.Data
        Table.Data tableData = TableDataBuilder.builder()
                .paginator(0, Constants.DEFAULT_PAGESIZE, tableRows.getTotalElements(), Arrays.asList(3, 5, 10, 20))
                .filterer(getFiltererFactory())
                .build();
        data.setTableData(tableData);

        return data;
    }


    private DemoComponentDTO initializeDataForSingleTableRow(String rowId)
    {
        // The data
        DemoComponentDTO data = new DemoComponentDTO();

        try
        {
            AddressEntity addressEntity = this.addressService.find(rowId);
            data.getTableDTO().setRows(this.addressMapper.map(List.of(addressEntity)));
        }
        catch (ResourceNotFoundException e)
        {
            // Just do nothing
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


    private FiltererFactory getFiltererFactory()
    {
        return FiltererFactory.builder()
                .filterer(AddressDTO.COL_POSTCODE, true, this.addressService::getDistinctPostcodes)
                .filterer(AddressDTO.COL_CITY, false, this.addressService::getDistinctCities)
                .filterer(AddressDTO.COL_STREET, true, this.addressService::getDistinctStreets)
                .filterer(AddressDTO.COL_DISTRICT, true, this.addressService::getDistinctDistricts);
    }

    /**
     * Returns the value set of a given column based on the searchText. Distinct values will be searched for with
     * where ... like '%searchText%' condition. Only columns with remote type ValueSets are allowed.
     *
     * @param column
     * @param searchText
     * @return
     */
    public Filterer getFilterer(String column, String searchText)
    {
        FiltererFactory filtererFactory = getFiltererFactory();
        return filtererFactory.getFilterer(column, searchText, false);
    }


//    private Filterer getFilterer(String column)
//    {
////        UserSettings userSettings = getUserSettings();
////        Optional<Map<String, Filterer>> optMap = Optional.of(userSettings).map(UserSettings::getExplorationTableSettings).map(Table.Data::getFiltererMap);
////        if (optMap.isPresent() && optMap.get().containsKey(column))
////        {
////            return optMap.get().get(column);
////        }
//
//        return getFilterer(column, "", true);
//    }
//
}
