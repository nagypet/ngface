/*
 * Copyright 2020-2025 the original author or authors.
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

package hu.perit.ngface.core.controller;

import hu.perit.ngface.core.types.intf.RowSelectParams;
import hu.perit.ngface.core.types.table.AbstractTableRow;
import hu.perit.ngface.core.types.table.SelectionStore;
import hu.perit.ngface.core.types.table.TableContent;
import hu.perit.ngface.core.types.table.TableSessionDefaults;
import hu.perit.ngface.core.widget.table.Filterer;
import hu.perit.ngface.core.widget.table.FiltererFactory;
import hu.perit.ngface.core.widget.table.Table;
import hu.perit.ngface.core.widget.table.TableDataBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
public abstract class TableControllerImpl<D, R extends AbstractTableRow<I>,I> implements TableController<D, R, I>
{

    protected Table.Data getTableData(Integer pageSize, Long length, List<Integer> pageSizeOptions)
    {
        TableSessionDefaults<R, I> sessionDefaults = getSessionDefaults();
        Table.Data defaults = Optional.of(sessionDefaults).map(TableSessionDefaults::getTableData).orElse(null);

        // Data
        return TableDataBuilder.builder(defaults)
            .paginator(0, pageSize, length, pageSizeOptions)
            .filterer(getFiltererFactory())
            .build();
    }


    protected void selectRowsInContent(TableContent<R> tableContent, long totalElements)
    {
        TableSessionDefaults<R, I> sessionDefaults = getSessionDefaults();

        // Updating selection states
        SelectionStore<R, I> selectionStore = Optional.of(sessionDefaults).map(TableSessionDefaults::getSelectionStore).orElse(null);
        if (selectionStore == null)
        {
            throw new IllegalStateException("SelectionStore is null!");
        }
        selectionStore.setTotalElements(totalElements);
        selectionStore.updateRowSelectionStates(tableContent.getRows());

        tableContent.setCountSelectedRows(selectionStore.getSelectedCount());
        tableContent.setSelectMode(selectionStore.getSelectMode());
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
        TableSessionDefaults<R, I> sessionDefaults = getSessionDefaults();
        Filterer filterer = Optional.of(sessionDefaults).map(TableSessionDefaults::getTableData).map(Table.Data::getFiltererMap).map(i -> i.get(column)).orElse(
            null);
        if (filterer != null && BooleanUtils.isTrue(filterer.getActive()))
        {
            return filterer;
        }

        FiltererFactory filtererFactory = getFiltererFactory();
        return filtererFactory.getFilterer(column, searchText, false);
    }


    @Override
    public void onRowSelect(RowSelectParams<I> rowSelectParams)
    {
        log.debug(rowSelectParams.toString());

        TableSessionDefaults<R, I> sessionDefaults = getSessionDefaults();
        SelectionStore<R, I> selectionStore = sessionDefaults.getSelectionStore();

        if (rowSelectParams.getSelectMode() == RowSelectParams.SelectMode.SINGLE)
        {
            selectionStore.singleRowsSelected(rowSelectParams.getRows());
        }
        else
        {
            selectionStore.setSelectMode(rowSelectParams.getSelectMode());
        }

        saveSessionDefaults(sessionDefaults);
    }


    protected void onSave(Table.Data data)
    {
        TableSessionDefaults<R, I> sessionDefaults = getSessionDefaults();
        if (filtererChanged(data.getFiltererMap(), sessionDefaults.getTableData().getFiltererMap()))
        {
            sessionDefaults.getSelectionStore().clearSingleSelections();
        }

        sessionDefaults.setTableData(data);
        saveSessionDefaults(sessionDefaults);
    }



    protected boolean filtererChanged(Map<String, Filterer> newFilterers, Map<String, Filterer> oldFilterers)
    {
        List<Filterer> newActive = newFilterers.values().stream().filter(i -> BooleanUtils.isTrue(i.getActive())).toList();
        List<Filterer> oldActive = oldFilterers.values().stream().filter(i -> BooleanUtils.isTrue(i.getActive())).toList();
        return !newActive.equals(oldActive);
    }
}
