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

import hu.perit.ngface.core.types.intf.DataRetrievalParams;
import hu.perit.ngface.core.types.intf.RowSelectParams;
import hu.perit.ngface.core.types.table.AbstractTableRow;
import hu.perit.ngface.core.types.table.SelectionStore;
import hu.perit.ngface.core.types.table.TableContent;
import hu.perit.ngface.core.types.table.TableSessionDefaults;
import hu.perit.ngface.core.widget.table.Filterer;
import hu.perit.ngface.core.widget.table.FiltererFactory;
import hu.perit.ngface.core.widget.table.Paginator;
import hu.perit.ngface.core.widget.table.Table;
import hu.perit.ngface.core.widget.table.TableDataBuilder;
import hu.perit.ngface.core.widget.table.ValueSet;
import hu.perit.spvitamin.core.typehelpers.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.Strings;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
public abstract class TableControllerImpl<D, R extends AbstractTableRow<I>, I extends Serializable> implements TableController<D, R, I>
{

    protected Table.Data getTableData(Integer pageSize, Long length, List<Integer> pageSizeOptions)
    {
        TableSessionDefaults<R, I> sessionDefaults = getSessionDefaults();
        Table.Data defaults = sessionDefaults.getTableData();

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
        SelectionStore<R, I> selectionStore = Optional.ofNullable(sessionDefaults).map(TableSessionDefaults::getSelectionStore).orElse(null);
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
        Filterer filterer = Optional.ofNullable(sessionDefaults.getTableData()).map(Table.Data::getFiltererMap).map(i -> i.get(column)).orElse(
                null);
        if (filterer != null && BooleanUtils.isTrue(filterer.getActive()) && useCachedFilter(searchText, filterer.getSearchText()))
        {
            return filterer;
        }

        FiltererFactory filtererFactory = getFiltererFactory();
        return filtererFactory.getFilterer(column, searchText, false);
    }


    private boolean useCachedFilter(String searchText, String filtererSearchText)
    {
        return Strings.CS.equals(searchText, filtererSearchText);
        //return StringUtils.isBlank(searchText) || Strings.CS.equals(searchText, filtererSearchText);
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


    @Override
    public void onRowDelete(List<I> deletedRowIds) throws Exception
    {
        if (deletedRowIds == null)
        {
            return;
        }
        log.debug(deletedRowIds.toString());

        TableSessionDefaults<R, I> sessionDefaults = getSessionDefaults();
        SelectionStore<R, I> selectionStore = sessionDefaults.getSelectionStore();

        // Unselecting the deleted row
        List<RowSelectParams.Row<I>> list = deletedRowIds.stream().map(i -> {
            RowSelectParams.Row<I> row = new RowSelectParams.Row<>();
            row.setId(i);
            row.setSelected(false);
            return row;
        }).toList();
        selectionStore.singleRowsSelected(list);

        saveSessionDefaults(sessionDefaults);
    }


    protected void onSave(Table.Data data)
    {
        TableSessionDefaults<R, I> sessionDefaults = getSessionDefaults();
        if (filtererChanged(data.getFiltererMap(), sessionDefaults.getTableData().getFiltererMap()))
        {
            sessionDefaults.getSelectionStore().clearSingleSelections();
        }

        // Fix out-of-bound page index
        if (data.getPaginator() != null)
        {
            data.paginator(Paginator.validPaginator(data.getPaginator()));
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


    protected List<DataRetrievalParams.Filter> getActiveFilters(String exceptForColumn)
    {
        TableSessionDefaults<R, I> sessionDefaults = getSessionDefaults();

        // Reading default filters
        List<DataRetrievalParams.Filter> filters = new ArrayList<>(sessionDefaults.getDefaultFilterers().stream().map(this::getFilterFromFilterer).toList());
        // Reading active filters from the session
        Map<String, Filterer> filtererMap = Optional.ofNullable(sessionDefaults.getTableData()).map(Table.Data::getFiltererMap).orElse(null);
        if (filtererMap == null)
        {
            return filters;
        }
        filters.addAll(filtererMap.values().stream()
                .filter(i -> BooleanUtils.isTrue(i.getActive()))
                .filter(i -> exceptForColumn == null || !exceptForColumn.equals(i.getColumn()))
                .map(this::getFilterFromFilterer)
                .toList());
        return filters;
    }


    protected List<DataRetrievalParams.Filter> getActiveFilters()
    {
        return this.getActiveFilters(null);
    }


    // Can be overridden
    protected DataRetrievalParams.Filter getFilterFromFilterer(Filterer filterer)
    {
        return DataRetrievalParams.Filter.of(filterer);
    }


    protected void addDefaultFilterer(DataRetrievalParams.Filter filter)
    {
        TableSessionDefaults<R, I> sessionDefaults = getSessionDefaults();
        List<Filterer> defaultFilterers = sessionDefaults.getDefaultFilterers();
        Map<String, Filterer> filtererMap = MapUtils.toMap(defaultFilterers, Filterer::getColumn);
        Filterer filterer = filtererMap.get(filter.getColumn());

        if (filterer == null)
        {
            filterer = new Filterer(filter.getColumn());
            defaultFilterers.add(filterer);
        }

        filterer.active(true);
        filterer.operator(filter.getOperator());
        filterer.valueSet(new ValueSet(false).values(filter.getValueSet().stream().map(i -> i.getText()).toList()));
        filterer.searchText(null);

        saveSessionDefaults(sessionDefaults);
    }


    protected void setFilter(String column, String searchText, String firstItem, String... moreItems)
    {
        TableSessionDefaults<R, I> sessionDefaults = getSessionDefaults();
        applyFilter(sessionDefaults, column, searchText, firstItem, moreItems);
        saveSessionDefaults(sessionDefaults);
    }


    protected void applyFilter(TableSessionDefaults<R, I> sessionDefaults, String column, String searchText, String firstItem, String... moreItems)
    {
        Filterer filterer = getFiltererFactory().getFilterer(column, searchText, true);
        if (filterer == null)
        {
            return;
        }
        Map<String, Filterer> filtererMap = sessionDefaults.getTableData().getFiltererMap();

        List<String> values = new ArrayList<>();
        values.add(firstItem);
        if (moreItems != null)
        {
            values.addAll(List.of(moreItems));
        }
        filterer.getValueSet().items(values.stream().map(i -> new ValueSet.Item().text(i).selected(true)).toList());
        filterer.searchText(searchText);
        filterer.active(true);

        filtererMap.put(column, filterer);
    }


    protected void clearSelections()
    {
        TableSessionDefaults<R, I> sessionDefaults = getSessionDefaults();
        sessionDefaults.getSelectionStore().clearSingleSelections();
        saveSessionDefaults(sessionDefaults);
    }
}
