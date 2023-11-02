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

package hu.perit.ngface.webservice.ngface.tabledemocomponent;

import hu.perit.ngface.core.controller.TableControllerImpl;
import hu.perit.ngface.core.types.intf.DataRetrievalParams;
import hu.perit.ngface.core.types.intf.TableActionParams;
import hu.perit.ngface.core.types.table.TableContent;
import hu.perit.ngface.core.types.table.TableSessionDefaults;
import hu.perit.ngface.core.widget.table.FiltererFactory;
import hu.perit.ngface.core.widget.table.Table;
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
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TableDemoComponentController extends TableControllerImpl<TableDemoComponentDTO, AddressTableRow, Long>
{
    private final AddressService addressService;
    private final AddressTableRowMapper addressTableRowMapper;
    private final SessionPersistenceService sessionPersistenceService;


    @Override
    public TableDemoComponentDTO getTable(DataRetrievalParams dataRetrievalParams)
    {
        TableDemoComponentDTO data = new TableDemoComponentDTO();

        // Content
        TableSessionDefaults<AddressTableRow, Long> sessionDefaults = getSessionDefaults();
        Table.Data defaults = Optional.of(sessionDefaults).map(TableSessionDefaults::getTableData).orElse(null);
        Page<AddressEntity> page = this.addressService.find(DataRetrievalParams.applyDefaults(dataRetrievalParams, defaults));
        long totalElements = 0;
        if (page != null)
        {
            data.getTableDTO().setContent(new TableContent<>(this.addressTableRowMapper.mapList(page.toList())));
            totalElements = page.getTotalElements();
        }

        // Data
        data.getTableDTO().setData(getTableData(Constants.DEFAULT_PAGESIZE, totalElements, Arrays.asList(3, 5, 10, 20)));

        // Selection
        selectRowsInContent(data.getTableDTO().getContent(), totalElements);

        // Notification
        data.getTableDTO().getContent().setNotification("This is a warning!");

        return data;
    }


    @Override
    public TableDemoComponentDTO getTableRow(Long rowId)
    {
        TableDemoComponentDTO data = new TableDemoComponentDTO();

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


    @Override
    public void onSave(TableDemoComponentDTO data)
    {
        super.onSave(data.getTableDTO().getData());
    }


    @Override
    public void onActionClick(TableActionParams tableActionParams) throws Exception
    {
        log.debug(tableActionParams.toString());
    }


    @Override
    public TableSessionDefaults<AddressTableRow, Long> getSessionDefaults()
    {
        return this.sessionPersistenceService.getSessionData().getDefaults();
    }


    @Override
    public void saveSessionDefaults(TableSessionDefaults<AddressTableRow, Long> defaults)
    {
        SessionData sessionData = this.sessionPersistenceService.getSessionData();
        sessionData.setDefaults(defaults);
        this.sessionPersistenceService.saveSessionData(sessionData);
    }


    @Override
    public FiltererFactory getFiltererFactory()
    {
        return FiltererFactory.builder()
            .filterer(AddressTableRow.COL_POSTCODE, true, this.addressService::getDistinctPostcodes)
            .filterer(AddressTableRow.COL_CITY, false, this.addressService::getDistinctCities)
            .filterer(AddressTableRow.COL_STREET, true, this.addressService::getDistinctStreets)
            .filterer(AddressTableRow.COL_DISTRICT, true, this.addressService::getDistinctDistricts);
    }
}
