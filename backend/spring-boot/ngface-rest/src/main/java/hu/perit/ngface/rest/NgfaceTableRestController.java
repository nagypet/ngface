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

package hu.perit.ngface.rest;

import hu.perit.ngface.core.controller.TableController;
import hu.perit.ngface.core.data.ComponentDTO;
import hu.perit.ngface.core.types.intf.DataRetrievalParams;
import hu.perit.ngface.core.types.intf.RowSelectParams;
import hu.perit.ngface.core.types.intf.SubmitFormData;
import hu.perit.ngface.core.types.intf.TableActionParams;
import hu.perit.ngface.core.view.ComponentView;
import hu.perit.ngface.core.widget.form.Form;
import hu.perit.ngface.core.widget.table.Filterer;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class NgfaceTableRestController<C extends TableController<D, ?, I>, D extends ComponentDTO, V extends ComponentView, I>
        implements NgfaceTableRestApi<I>
{
    private final C tableController;


    @Override
    public Form getTable(DataRetrievalParams dataRetrievalParams)
    {
        D data = this.tableController.getTable(dataRetrievalParams);
        return supplyView(data).getForm();
    }


    @Override
    public Form getTableRow(I rowId)
    {
        D data = this.tableController.getTableRow(rowId);
        return supplyView(data).getForm();
    }


    @Override
    public Filterer getColumnFilterer(String column, String searchText)
    {
        return this.tableController.getFilterer(column, searchText);
    }


    @Override
    public void onRowSelect(RowSelectParams<I> rowSelectParams) throws Exception
    {
        this.tableController.onRowSelect(rowSelectParams);
    }

    @Override
    public void onActionClick(TableActionParams<I> tableActionParams) throws Exception
    {
        this.tableController.onActionClick(tableActionParams);
    }

    @Override
    public void submitTable(SubmitFormData submitFormData)
    {
        D data = supplyDTO();
        data.formSubmitted(submitFormData);
        this.tableController.onSave(data);
    }


    protected abstract V supplyView(D data);

    protected abstract D supplyDTO();
}
