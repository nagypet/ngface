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

package hu.perit.ngface.core.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hu.perit.ngface.core.widget.base.WidgetData;
import hu.perit.ngface.core.widget.exception.NgFaceBadRequestException;
import hu.perit.ngface.core.widget.input.DateInput;
import hu.perit.ngface.core.widget.input.NumericInput;
import hu.perit.ngface.core.widget.input.TextInput;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Getter
@Setter
public class SubmitFormData
{
    @NotNull
    private String id;
    @NotNull
    private Map<String, WidgetData> widgetDataMap;

    @JsonIgnore
    public <T extends WidgetData> T get(String id, Class<T> dataClass)
    {
        if (this.widgetDataMap == null || !this.widgetDataMap.containsKey(id))
        {
            throw new NgFaceBadRequestException(String.format("Widget id '%s' does not exist in submitted data!", id));
        }
        WidgetData widgetData = this.widgetDataMap.get(id);
        if (!widgetData.getClass().isAssignableFrom(dataClass))
        {
            throw new NgFaceBadRequestException(String.format("Widget type mismatch! Expected type: %s, actual type %s", dataClass.getSimpleName(), widgetData.getClass().getSimpleName()));
        }
        return (T) widgetData;
    }


    @JsonIgnore
    public String getTextInputValue(String id)
    {
        return get(id, TextInput.Data.class).getValue();
    }


    @JsonIgnore
    public BigDecimal getNumericInputValue(String id)
    {
        return get(id, NumericInput.Data.class).getValue();
    }


    @JsonIgnore
    public LocalDate getDateInputValue(String id)
    {
        return get(id, DateInput.Data.class).getValue();
    }


    @JsonIgnore
    public boolean isEmpty()
    {
        return this.widgetDataMap == null || this.widgetDataMap.isEmpty();
    }


    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder("SubmitFormData{");
        sb.append("id='").append(id).append('\'');
        sb.append(", widgetDataMap=[");
        if (!isEmpty())
        {
            for (Map.Entry<String, WidgetData> entry : this.widgetDataMap.entrySet())
            {
                sb.append(String.format("%n\t'%s' => %s", entry.getKey(), entry.getValue().toString()));
            }
        }
        sb.append("]}");
        return sb.toString();
    }
}
