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

package hu.perit.ngface.core.widget.table;

import hu.perit.ngface.core.formating.NumericFormat;
import hu.perit.ngface.core.widget.table.cell.Cell;
import hu.perit.ngface.core.widget.table.cell.NumericCell;
import hu.perit.ngface.core.widget.table.cell.TextCell;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.BooleanUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

@RequiredArgsConstructor
@ToString
@Getter
public class Row
{
    private final String id;
    private final Map<String, Cell<?, ?>> cells = new LinkedHashMap<>();
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

    public Row putCell(String colId, LocalDateTime localDateTime)
    {
        if (localDateTime != null)
        {
            String text = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            this.cells.put(colId, new TextCell(text));
        }
        else
        {
            this.cells.put(colId, new TextCell(""));
        }
        return this;
    }

    public Row putCell(String colId, LocalDate localDate)
    {
        if (localDate != null)
        {
            String text = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            this.cells.put(colId, new TextCell(text));
        }
        else
        {
            this.cells.put(colId, new TextCell(""));
        }
        return this;
    }

    public Row putCell(String colId, BigDecimal value)
    {
        this.cells.put(colId, new NumericCell(value));
        return this;
    }

    public Row putCell(String colId, BigDecimal value, NumericFormat format)
    {
        this.cells.put(colId, new NumericCell(value).format(format));
        return this;
    }

    public Row putCell(String colId, Integer value)
    {
        this.cells.put(colId, new NumericCell(value != null ? new BigDecimal(value) : null));
        return this;
    }

    public Row putCell(String colId, Integer value, NumericFormat format)
    {
        this.cells.put(colId, new NumericCell(value != null ? new BigDecimal(value) : null).format(format));
        return this;
    }

    public Row putCell(String colId, Long value)
    {
        this.cells.put(colId, new NumericCell(value != null ? new BigDecimal(value) : null));
        return this;
    }

    public Row putCell(String colId, Long value, NumericFormat format)
    {
        this.cells.put(colId, new NumericCell(value != null ? new BigDecimal(value) : null).format(format));
        return this;
    }

    public Row putCell(String colId, Float value)
    {
        this.cells.put(colId, new NumericCell(value != null ? BigDecimal.valueOf(value) : null));
        return this;
    }

    public Row putCell(String colId, Float value, NumericFormat format)
    {
        this.cells.put(colId, new NumericCell(value != null ? BigDecimal.valueOf(value) : null).format(format));
        return this;
    }

    public Row putCell(String colId, Double value)
    {
        this.cells.put(colId, new NumericCell(value != null ? BigDecimal.valueOf(value) : null));
        return this;
    }

    public Row putCell(String colId, Double value, NumericFormat format)
    {
        this.cells.put(colId, new NumericCell(value != null ? BigDecimal.valueOf(value) : null).format(format));
        return this;
    }

    public Row putCell(String colId, Boolean value)
    {
        this.cells.put(colId, new TextCell(convertBooleanToString(value)));
        return this;
    }

    private static String convertBooleanToString(Boolean value)
    {
        if (value == null)
        {
            return null;
        }

        return BooleanUtils.isTrue(value) ? "1" : "0";
    }

    public Row putCell(String colId, Cell<?, ?> cell)
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
