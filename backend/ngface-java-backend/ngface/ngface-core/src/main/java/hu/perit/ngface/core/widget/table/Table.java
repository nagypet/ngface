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

package hu.perit.ngface.core.widget.table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hu.perit.ngface.core.types.intf.Direction;
import hu.perit.ngface.core.widget.base.Widget;
import hu.perit.ngface.core.widget.base.WidgetData;
import hu.perit.ngface.core.widget.exception.NgFaceException;
import lombok.*;

import javax.annotation.Nullable;
import java.util.*;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Table<T> extends Widget<Table.Data, Table<T>>
{
    public enum SelectMode
    {
        NONE,
        SINGLE,
        MULTI,
        CHECKBOX
    }

    private final Map<String, ColumnGroup> columnGroups = new LinkedHashMap<>();
    private final Map<String, Column> columns = new LinkedHashMap<>();
    private final List<Row<T>> rows = new ArrayList<>();
    @Nullable
    private Row<T> totalRow;
    private SelectMode selectMode = SelectMode.NONE;
    // Notification may contain HTML tags!
    private String notification;


    public Table(String id)
    {
        super(id);
        this.data = new Table.Data();
    }


    // Json
    private Table()
    {
        super(null);
        this.data = new Table.Data();
    }

    public Table<T> addColumnGroup(ColumnGroup columnGroup)
    {
        if (!this.rows.isEmpty())
        {
            throw new NgFaceException("Please define columns before adding rows!");
        }

        if (!columnGroup.isValid())
        {
            throw new NgFaceException("columnGroup is invalid!");
        }

        this.columnGroups.put(columnGroup.getId(), columnGroup);
        return this;
    }


    public Table<T> addColumn(Column column)
    {
        if (!this.rows.isEmpty())
        {
            throw new NgFaceException("Please define columns before adding rows!");
        }

        this.columns.put(column.getId(), column);
        return this;
    }


    public Table<T> addRow(Row<T> row)
    {
        if (!this.columns.keySet().equals(row.getCells().keySet()))
        {
            throw new NgFaceException("Columns in 'row' property does not match with defined columns!");
        }

        this.rows.add(row);
        return this;
    }


    public Table<T> totalRow(Row<T> row)
    {
        if (!this.columns.keySet().containsAll(row.getCells().keySet()))
        {
            throw new NgFaceException("Columns in 'row' property does not match with defined columns!");
        }

        this.totalRow = row;
        return this;
    }


    public Table<T> selectMode(SelectMode selectMode)
    {
        this.selectMode = selectMode;
        return this;
    }


    public Table<T> notification(String notification)
    {
        this.notification = notification;
        return this;
    }


    @ToString(callSuper = true)
    @Getter
    @EqualsAndHashCode(callSuper = true)
    public static class Data extends WidgetData
    {
        @Nullable
        private Paginator paginator;
        @Nullable
        private Sorter sorter;
        @Setter(AccessLevel.NONE)
        private final Map<String, Filterer> filtererMap = new HashMap<>();


        public Data paginator(Paginator paginator)
        {
            this.paginator = paginator;
            return this;
        }


        public Data sorter(Sorter sorter)
        {
            this.sorter = sorter;
            return this;
        }


        public Data addFilterer(@NonNull Filterer filterer)
        {
            this.filtererMap.put(filterer.getColumn(), filterer);
            return this;
        }


        @JsonIgnore
        public int getPageIndex()
        {
            if (this.paginator == null)
            {
                return 0;
            }

            return this.paginator.getPageIndex() != null ? this.paginator.getPageIndex() : 0;
        }


        @JsonIgnore
        public int getPageSize(int defaultPageSize)
        {
            if (this.paginator == null)
            {
                return defaultPageSize;
            }

            return this.paginator.getPageSize() != null ? this.paginator.getPageSize() : defaultPageSize;
        }


        @JsonIgnore
        public long getPaginatorLength()
        {
            if (this.paginator == null)
            {
                return 0;
            }

            return this.paginator.getLength() != null ? this.paginator.getLength() : 0;
        }


        @JsonIgnore
        public String getSortColumn()
        {
            return this.sorter != null ? this.sorter.getColumn() : null;
        }


        @JsonIgnore
        public Direction getSortDirection()
        {
            return this.sorter != null ? this.sorter.getDirection() : Direction.UNDEFINED;
        }
    }
}
