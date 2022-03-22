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

import hu.perit.ngface.widget.base.Widget;
import hu.perit.ngface.widget.base.WidgetData;
import hu.perit.ngface.widget.exception.NgFaceException;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Getter
@ToString(callSuper = true)
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
    private Paginator paginator;
    private SelectMode selectMode = SelectMode.NONE;

    public Table(String id)
    {
        super(id);
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
            throw new NgFaceException("The 'row' property contains uknkown column ids!");
        }

        this.rows.add(row);
        return this;
    }

    public Table paginator(Paginator paginator)
    {
        this.paginator = paginator;
        return this;
    }


    public Table selectMode(SelectMode selectMode)
    {
        this.selectMode = selectMode;
        return this;
    }

    @ToString(callSuper = true)
    @Getter
    public static class Data extends WidgetData
    {
    }
}
