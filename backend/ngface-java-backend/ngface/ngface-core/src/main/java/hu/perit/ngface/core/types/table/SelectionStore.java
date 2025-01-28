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

package hu.perit.ngface.core.types.table;

import hu.perit.ngface.core.types.intf.RowSelectParams;
import hu.perit.ngface.core.widget.table.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.BooleanUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class can store the selection state of a data table in an optimized way. There is a default selectMode, which can ba ALL_UNCHECKED or ALL_CHECKED.
 * The rowMap stores only the individual differences to the default selectMode. In that way we can handle huge tables without the need of storing the
 * selection state of each individual record.
 *
 * @param <T>
 * @param <I>
 */
@ToString
@RequiredArgsConstructor
public class SelectionStore<T extends AbstractTableRow<I>, I>
{
    private final Table.SelectMode tableSelectMode;

    @Getter
    private RowSelectParams.SelectMode selectMode = RowSelectParams.SelectMode.ALL_UNCHECKED;
    private final Map<I, RowSelectParams.Row<I>> rowMap = new HashMap<>();
    private long totalElements = 0;


    public void singleRowsSelected(List<RowSelectParams.Row<I>> rows)
    {
        if (tableSelectMode == Table.SelectMode.NONE)
        {
            return;
        }
        if (tableSelectMode == Table.SelectMode.SINGLE && !rows.isEmpty())
        {
            this.rowMap.clear();
            RowSelectParams.Row<I> row = rows.get(0);
            this.rowMap.put(row.getId(), row);
            return;
        }

        // Multi-select
        for (RowSelectParams.Row<I> row : rows)
        {
            if (this.rowMap.containsKey(row.getId()))
            {
                this.rowMap.get(row.getId()).setSelected(row.getSelected());
            }
            else
            {
                this.rowMap.put(row.getId(), row);
            }
        }
    }


    public void clearSingleSelections()
    {
        this.rowMap.clear();
    }


    public void setSelectMode(RowSelectParams.SelectMode selectMode)
    {
        if (selectMode == RowSelectParams.SelectMode.ALL_CHECKED || selectMode == RowSelectParams.SelectMode.ALL_UNCHECKED)
        {
            this.selectMode = selectMode;
            this.rowMap.clear();
        }
    }


    public Long getSelectedCount()
    {
        if (this.selectMode == RowSelectParams.SelectMode.ALL_UNCHECKED)
        {
            return this.rowMap.values().stream().filter(i -> BooleanUtils.isTrue(i.getSelected())).count();
        }
        else
        {
            return totalElements - this.rowMap.values().stream().filter(i -> BooleanUtils.isNotTrue(i.getSelected())).count();
        }
    }


    public void setTotalElements(Long totalElements)
    {
        if (totalElements != null)
        {
            this.totalElements = totalElements.longValue();
        }
    }


    public void updateRowSelectionStates(List<T> rows)
    {
        for (T row : rows)
        {
            row.setSelected(isSelected(row.getId()));
        }
    }


    public boolean isSelected(I id)
    {
        if (id == null)
        {
            return false;
        }

        if (this.selectMode == RowSelectParams.SelectMode.ALL_UNCHECKED)
        {
            return this.rowMap.containsKey(id) && BooleanUtils.isTrue(this.rowMap.get(id).getSelected());
        }
        else
        {
            return !this.rowMap.containsKey(id) || BooleanUtils.isTrue(this.rowMap.get(id).getSelected());
        }
    }


    public List<I> getSelectedRowIds()
    {
        return this.rowMap.values().stream()
            .filter(i -> BooleanUtils.isTrue(i.getSelected()))
            .map(RowSelectParams.Row::getId)
            .toList();
    }
}
