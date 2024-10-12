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

package hu.perit.ngface.core.widget.base;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import hu.perit.ngface.core.widget.button.Button;
import hu.perit.ngface.core.widget.formattedtext.FormattedText;
import hu.perit.ngface.core.widget.input.*;
import hu.perit.ngface.core.widget.table.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * @author Peter Nagy
 */

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = TextInput.class, name = "TextInput"),
        @JsonSubTypes.Type(value = NumericInput.class, name = "NumericInput"),
        @JsonSubTypes.Type(value = DateInput.class, name = "DateInput"),
        @JsonSubTypes.Type(value = DateTimeInput.class, name = "DateTimeInput"),
        @JsonSubTypes.Type(value = DateRangeInput.class, name = "DateRangeInput"),
        @JsonSubTypes.Type(value = Select.class, name = "Select"),
        @JsonSubTypes.Type(value = Table.class, name = "Table"),
        @JsonSubTypes.Type(value = FormattedText.class, name = "FormattedText"),
        @JsonSubTypes.Type(value = Autocomplete.class, name = "Autocomplete"),
        @JsonSubTypes.Type(value = Button.class, name = "Button")
})
public abstract class Widget<WD extends WidgetData, SUB extends Widget>
{
    private final String type = getClass().getSimpleName();
    private final String id;
    protected String label;
    protected String hint;
    protected boolean enabled = true;
    protected WD data;

    // Json
    private Widget()
    {
        this.id = null;
    }

    public SUB label(String label)
    {
        this.label = label;
        return (SUB) this;
    }

    public SUB hint(String hint)
    {
        this.hint = hint;
        return (SUB) this;
    }

    public SUB enabled(boolean enabled)
    {
        this.enabled = enabled;
        return (SUB) this;
    }

    public SUB data(WD data)
    {
        this.data = data;
        return (SUB) this;
    }
}
