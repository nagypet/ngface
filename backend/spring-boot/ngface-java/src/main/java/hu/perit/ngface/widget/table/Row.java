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

import hu.perit.ngface.widget.table.cell.Cell;
import hu.perit.ngface.widget.table.cell.TextCell;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.BooleanUtils;

import java.util.LinkedHashMap;
import java.util.Map;

@RequiredArgsConstructor
@ToString
@Getter
public class Row
{
    private final String id;
    private final Map<String, Cell<?>> cells = new LinkedHashMap<>();
    private boolean selected;

    // For JSon deserialization
    private Row()
    {
        this.id = null;
    }

    public Row putCell(String colId, String text)
    {
        this.cells.put(colId, new TextCell(text));
        return this;
    }

    public Row putCell(String colId, Cell<?> cell)
    {
        this.cells.put(colId, cell);
        return this;
    }

    public Row selected(Boolean selected)
    {
        this.selected = BooleanUtils.isTrue(selected);
        return this;
    }
}
