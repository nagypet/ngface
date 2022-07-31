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

package hu.perit.ngface.widget.table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hu.perit.ngface.data.Direction;
import hu.perit.ngface.widget.base.Widget;
import hu.perit.ngface.widget.base.WidgetData;
import hu.perit.ngface.widget.exception.NgFaceException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Table extends Widget<Table.Data, Table>
{
    public enum SelectMode
    {
        NONE,
        SINGLE,
        MULTI,
        CHECKBOX
    }

    private final Map<String, Column> columns = new LinkedHashMap<>();
    private final List<Row> rows = new ArrayList<>();
    private SelectMode selectMode = SelectMode.NONE;
    // Notification may contain HTML tags!
    private String notification;

    public Table(String id)
    {
        super(id);
        this.data = new Table.Data();
    }

    public Table addColumn(Column column)
    {
        if (!this.rows.isEmpty())
        {
            throw new NgFaceException("Please define columns before adding rows!");
        }

        this.columns.put(column.getId(), column);
        return this;
    }

    public Table addRow(Row row)
    {
        if (!this.columns.keySet().equals(row.getCells().keySet()))
        {
            throw new NgFaceException("Columns in 'row' property does not match with defined columns!");
        }

        this.rows.add(row);
        return this;
    }

    public Table selectMode(SelectMode selectMode)
    {
        this.selectMode = selectMode;
        return this;
    }

    public Table notification(String notification)
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
        private Map<String, Filterer> filtererMap = new HashMap<>();

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
