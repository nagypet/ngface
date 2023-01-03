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

package hu.perit.ngface.widget.base;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import hu.perit.ngface.widget.input.*;
import hu.perit.ngface.widget.table.Table;
import hu.perit.ngface.widget.formattedtext.FormattedText;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonSubTypes({ //
        @JsonSubTypes.Type(value = TextInput.Data.class, name = "TextInput.Data"),
        @JsonSubTypes.Type(value = NumericInput.Data.class, name = "NumericInput.Data"),
        @JsonSubTypes.Type(value = DateInput.Data.class, name = "DateInput.Data"),
        @JsonSubTypes.Type(value = DateTimeInput.Data.class, name = "DateTimeInput.Data"),
        @JsonSubTypes.Type(value = DateRangeInput.Data.class, name = "DateRangeInput.Data"),
        @JsonSubTypes.Type(value = Select.Data.class, name = "Select.Data"),
        @JsonSubTypes.Type(value = Table.Data.class, name = "Table.Data"),
        @JsonSubTypes.Type(value = FormattedText.Data.class, name = "FormattedText.Data")
})
@EqualsAndHashCode
public class WidgetData
{
    private final String type = getTypeName();

    private String getTypeName()
    {
        String packageName = getClass().getPackage().getName();
        return getClass().getCanonicalName().substring(packageName.length() + 1);
    }
}
