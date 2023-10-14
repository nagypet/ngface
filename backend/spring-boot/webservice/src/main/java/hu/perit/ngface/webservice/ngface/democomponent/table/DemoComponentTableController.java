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

package hu.perit.ngface.webservice.ngface.democomponent.table;

import hu.perit.ngface.core.controller.TableController;
import hu.perit.ngface.core.types.intf.DataRetrievalParams;
import hu.perit.ngface.core.types.intf.RowSelectParams;
import hu.perit.ngface.core.types.intf.TableActionParams;
import hu.perit.ngface.core.types.table.SelectionStore;
import hu.perit.ngface.core.types.table.TableContent;
import hu.perit.ngface.core.widget.table.Filterer;
import hu.perit.ngface.core.widget.table.FiltererFactory;
import hu.perit.ngface.core.widget.table.Table;
import hu.perit.ngface.core.widget.table.TableDataBuilder;
import hu.perit.ngface.webservice.config.Constants;
import hu.perit.ngface.webservice.db.addressdb.table.AddressEntity;
import hu.perit.ngface.webservice.mapper.AddressTableRowMapper;
import hu.perit.ngface.webservice.model.AddressTableRow;
import hu.perit.ngface.webservice.service.api.AddressService;
import hu.perit.ngface.webservice.service.api.SessionData;
import hu.perit.ngface.webservice.service.api.SessionPersistenceService;
import hu.perit.spvitamin.spring.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DemoComponentTableController implements TableController<DemoComponentTableDTO, Long>
{
    private final AddressService addressService;
    private final AddressTableRowMapper addressTableRowMapper;
    private final SessionPersistenceService sessionPersistenceService;


    @Override
    public DemoComponentTableDTO getTable(DataRetrievalParams dataRetrievalParams)
    {
        DemoComponentTableDTO data = new DemoComponentTableDTO();

        // Rows
        SessionData sessionData = this.sessionPersistenceService.getSessionData();
        Table.Data defaults = Optional.of(sessionData).map(SessionData::getTableData).orElse(null);
        Page<AddressEntity> page = this.addressService.find(DataRetrievalParams.applyDefaults(dataRetrievalParams, defaults));
        long totalElements = 0;
        if (page != null)
        {
            data.getTableDTO().setContent(new TableContent<>(this.addressTableRowMapper.mapList(page.toList())));
            totalElements = page.getTotalElements();
        }

        // Data
        data.getTableDTO().setData(TableDataBuilder.builder(defaults)
            .paginator(0, Constants.DEFAULT_PAGESIZE, totalElements, Arrays.asList(3, 5, 10, 20))
            .filterer(getFiltererFactory())
            .build());

        // Updating selection states
        SelectionStore<AddressTableRow, Long> selectionStore = getSelectionStore();
        selectionStore.setTotalElements(totalElements);
        selectionStore.updateRowSelectionStates(data.getTableDTO().getContent().getRows());

        data.getTableDTO().getContent().setCountSelectedRows(selectionStore.getSelectedCount());
        data.getTableDTO().getContent().setSelectMode(selectionStore.getSelectMode());

        return data;
    }


    @Override
    public DemoComponentTableDTO getTableRow(Long rowId)
    {
        DemoComponentTableDTO data = new DemoComponentTableDTO();

        try
        {
            AddressEntity addressEntity = this.addressService.find(rowId);
            data.getTableDTO().setContent(new TableContent<>(List.of(this.addressTableRowMapper.map(addressEntity))));
        }
        catch (ResourceNotFoundException e)
        {
            // Just do nothing
        }

        return data;
    }


    /**
     * Returns the value set of a given column based on the searchText. Distinct values will be searched for with
     * where ... like '%searchText%' condition. Only columns with remote type ValueSets are allowed.
     *
     * @param column
     * @param searchText
     * @return
     */
    @Override
    public Filterer getFilterer(String column, String searchText)
    {
        SessionData sessionData = this.sessionPersistenceService.getSessionData();
        Filterer filterer = Optional.of(sessionData).map(SessionData::getTableData).map(Table.Data::getFiltererMap).map(i -> i.get(column)).orElse(null);
        if (filterer != null && BooleanUtils.isTrue(filterer.getActive()))
        {
            return filterer;
        }

        FiltererFactory filtererFactory = getFiltererFactory();
        return filtererFactory.getFilterer(column, searchText, false);
    }


    @Override
    public void onSave(DemoComponentTableDTO data)
    {
        SessionData sessionData = this.sessionPersistenceService.getSessionData();
        if (filtererChanged(data.getTableDTO().getData().getFiltererMap(), sessionData.getTableData().getFiltererMap()))
        {
            sessionData.getSelectionStore().clearSingleSelections();
        }

        sessionData.setTableData(data.getTableDTO().getData());
        this.sessionPersistenceService.saveSessionData(sessionData);
    }


    private boolean filtererChanged(Map<String, Filterer> newFilterers, Map<String, Filterer> oldFilterers)
    {
        List<Filterer> newActive = newFilterers.values().stream().filter(i -> BooleanUtils.isTrue(i.getActive())).toList();
        List<Filterer> oldActive = oldFilterers.values().stream().filter(i -> BooleanUtils.isTrue(i.getActive())).toList();
        return !newActive.equals(oldActive);
    }

    @Override
    public void onActionClick(TableActionParams tableActionParams) throws Exception
    {
        log.debug(tableActionParams.toString());
    }


    @Override
    public void onRowSelect(RowSelectParams<Long> rowSelectParams)
    {
        log.debug(rowSelectParams.toString());

        SelectionStore<AddressTableRow, Long> selectionStore = getSelectionStore();

        if (rowSelectParams.getSelectMode() == RowSelectParams.SelectMode.SINGLE)
        {
            selectionStore.singleRowsSelected(rowSelectParams.getRows());
        }
        else
        {
            selectionStore.setSelectMode(rowSelectParams.getSelectMode());
        }

        saveSelectionStore(selectionStore);
    }


    private SelectionStore<AddressTableRow, Long> getSelectionStore()
    {
        SessionData sessionData = this.sessionPersistenceService.getSessionData();
        return sessionData.getSelectionStore();
    }


    private void saveSelectionStore(SelectionStore<AddressTableRow, Long> selectionStore)
    {
        SessionData sessionData = this.sessionPersistenceService.getSessionData();
        sessionData.setSelectionStore(selectionStore);
        this.sessionPersistenceService.saveSessionData(sessionData);
    }

    private FiltererFactory getFiltererFactory()
    {
        return FiltererFactory.builder()
            .filterer(AddressTableRow.COL_POSTCODE, true, this.addressService::getDistinctPostcodes)
            .filterer(AddressTableRow.COL_CITY, false, this.addressService::getDistinctCities)
            .filterer(AddressTableRow.COL_STREET, true, this.addressService::getDistinctStreets)
            .filterer(AddressTableRow.COL_DISTRICT, true, this.addressService::getDistinctDistricts);
    }
}
