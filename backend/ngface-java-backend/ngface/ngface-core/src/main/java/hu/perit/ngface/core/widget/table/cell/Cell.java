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

package hu.perit.ngface.core.widget.table.cell;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ActionCell.class, name = "ActionCell"),
        @JsonSubTypes.Type(value = NumericCell.class, name = "NumericCell"),
        @JsonSubTypes.Type(value = TextCell.class, name = "TextCell")
})
@EqualsAndHashCode
public abstract class Cell<V, SUB extends Cell>
{
    private final String type = getClass().getSimpleName();
    protected final V value;
    protected String label;
    protected String style;
    protected Icon icon;


    // Json
    private Cell()
    {
        this.value = null;
    }


    public SUB label(String label)
    {
        this.label = label;
        return (SUB) this;
    }


    public SUB style(String style)
    {
        this.style = style;
        return (SUB) this;
    }


    public SUB icon(Icon icon)
    {
        this.icon = icon;
        return (SUB) this;
    }


    @Builder
    @Getter
    @EqualsAndHashCode
    public static class Icon
    {
        public enum Placement
        {
            BEFORE,
            AFTER
        }

        private String code;
        private Placement placement;
        private String color;
        private String label;
    }
}
