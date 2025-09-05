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

package hu.perit.ngface.core.widget.table;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import hu.perit.ngface.core.formating.NumericFormat;
import hu.perit.ngface.core.widget.table.cell.Cell;
import hu.perit.ngface.core.widget.table.cell.NumericCell;
import hu.perit.ngface.core.widget.table.cell.TextCell;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

@ToString
@Getter
@EqualsAndHashCode
public class Row<T>
{
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = "idType")
    private final T id;
    private String idType;
    private final Map<String, Cell<?, ?>> cells = new LinkedHashMap<>();
    private boolean selected;
    private boolean disabled;


    // For JSon deserialization
    private Row()
    {
        this.id = null;
    }


    public Row(T id)
    {
        this.id = id;
        if (id != null)
        {
            this.idType = id.getClass().getName();
        }
    }


    public Row<T> putCell(String colId, String text)
    {
        this.cells.put(colId, new TextCell(text));
        return this;
    }


    public Row<T> putCell(String colId, String text, int maxWidth)
    {
        TextCell textCell;
        if (StringUtils.length(text) < maxWidth)
        {
            textCell = new TextCell(text);
        }
        else
        {
            textCell = new TextCell(StringUtils.abbreviate(text, maxWidth)).label(text);
        }
        this.cells.put(colId, textCell);
        return this;
    }


    public Row<T> putCell(String colId, String text, String style, Cell.Icon icon)
    {
        this.cells.put(colId, new TextCell(text).style(style).icon(icon));
        return this;
    }


    public Row<T> putCell(String colId, OffsetDateTime offsetDateTime)
    {
        if (offsetDateTime != null)
        {
            String text = offsetDateTime.toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            this.cells.put(colId, new TextCell(text));
        }
        else
        {
            this.cells.put(colId, new TextCell(""));
        }
        return this;
    }


    public Row<T> putCell(String colId, LocalDateTime localDateTime)
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


    public Row<T> putCell(String colId, LocalDate localDate)
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


    public Row<T> putCell(String colId, BigDecimal value)
    {
        this.cells.put(colId, new NumericCell(value));
        return this;
    }


    public Row<T> putCell(String colId, BigDecimal value, NumericFormat format)
    {
        this.cells.put(colId, new NumericCell(value).format(format));
        return this;
    }


    public Row<T> putCell(String colId, Integer value)
    {
        this.cells.put(colId, new NumericCell(value != null ? new BigDecimal(value) : null));
        return this;
    }


    public Row<T> putCell(String colId, Integer value, NumericFormat format)
    {
        this.cells.put(colId, new NumericCell(value != null ? new BigDecimal(value) : null).format(format));
        return this;
    }


    public Row<T> putCell(String colId, Long value)
    {
        this.cells.put(colId, new NumericCell(value != null ? new BigDecimal(value) : null));
        return this;
    }


    public Row<T> putCell(String colId, Long value, NumericFormat format)
    {
        this.cells.put(colId, new NumericCell(value != null ? new BigDecimal(value) : null).format(format));
        return this;
    }


    public Row<T> putCell(String colId, Float value)
    {
        this.cells.put(colId, new NumericCell(value != null ? BigDecimal.valueOf(value) : null));
        return this;
    }


    public Row<T> putCell(String colId, Float value, NumericFormat format)
    {
        this.cells.put(colId, new NumericCell(value != null ? BigDecimal.valueOf(value) : null).format(format));
        return this;
    }


    public Row<T> putCell(String colId, Double value)
    {
        this.cells.put(colId, new NumericCell(value != null ? BigDecimal.valueOf(value) : null));
        return this;
    }


    public Row<T> putCell(String colId, Double value, NumericFormat format)
    {
        this.cells.put(colId, new NumericCell(value != null ? BigDecimal.valueOf(value) : null).format(format));
        return this;
    }


    public Row<T> putCell(String colId, Boolean value)
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


    public Row<T> putCell(String colId, Cell<?, ?> cell)
    {
        this.cells.put(colId, cell);
        return this;
    }


    public Row<T> selected(Boolean selected)
    {
        this.selected = BooleanUtils.isTrue(selected);
        return this;
    }


    public Row<T> disabled(Boolean disabled)
    {
        this.disabled = BooleanUtils.isTrue(disabled);
        return this;
    }
}
