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

package hu.perit.ngface.webservice.rest.controller;

import hu.perit.ngface.core.types.intf.DataRetrievalParams;
import hu.perit.ngface.core.types.intf.RowSelectParams;
import hu.perit.ngface.core.types.intf.SubmitFormData;
import hu.perit.ngface.core.widget.form.Form;
import hu.perit.ngface.core.widget.table.Filterer;
import hu.perit.ngface.rest.NgfaceTableRestController;
import hu.perit.ngface.webservice.config.Constants;
import hu.perit.ngface.webservice.ngface.tabledemocomponent.TableDemoComponentController;
import hu.perit.ngface.webservice.ngface.tabledemocomponent.TableDemoComponentDTO;
import hu.perit.ngface.webservice.ngface.tabledemocomponent.TableDemoComponentView;
import hu.perit.spvitamin.spring.restmethodlogger.LoggedRestMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/frontend/forms/table-form")
public class TableDemoFormRestController extends NgfaceTableRestController<TableDemoComponentController, TableDemoComponentDTO, TableDemoComponentView, Long>
{
    public TableDemoFormRestController(TableDemoComponentController tableController)
    {
        super(tableController);
    }


    @Override
    @LoggedRestMethod(eventId = Constants.TABLE_DEMO_FORM_CONTROLLER_GET_TABLE, subsystem = Constants.SUBSYSTEM_NAME)
    public Form getTable(DataRetrievalParams dataRetrievalParams)
    {
        return super.getTable(dataRetrievalParams);
    }


    @Override
    @LoggedRestMethod(eventId = Constants.TABLE_DEMO_FORM_CONTROLLER_GET_TABLE_ROW, subsystem = Constants.SUBSYSTEM_NAME)
    public Form getTableRow(Long rowId)
    {
        return super.getTableRow(rowId);
    }


    @Override
    @LoggedRestMethod(eventId = Constants.TABLE_DEMO_FORM_CONTROLLER_GET_COLUMN_FILTERER, subsystem = Constants.SUBSYSTEM_NAME)
    public Filterer getColumnFilterer(String column, String searchText)
    {
        return super.getColumnFilterer(column, searchText);
    }


    @Override
    @LoggedRestMethod(eventId = Constants.TABLE_DEMO_FORM_CONTROLLER_ON_ROW_SELECT, subsystem = Constants.SUBSYSTEM_NAME)
    public void onRowSelect(RowSelectParams<Long> rowSelectParams) throws Exception
    {
        super.onRowSelect(rowSelectParams);
    }


    @Override
    @LoggedRestMethod(eventId = Constants.TABLE_DEMO_FORM_CONTROLLER_SUBMIT_TABLE, subsystem = Constants.SUBSYSTEM_NAME)
    public void submitTable(SubmitFormData submitFormData)
    {
        super.submitTable(submitFormData);
    }


    @Override
    protected TableDemoComponentView supplyView(TableDemoComponentDTO data)
    {
        return new TableDemoComponentView(data);
    }


    @Override
    protected TableDemoComponentDTO supplyDTO()
    {
        return new TableDemoComponentDTO();
    }
}
