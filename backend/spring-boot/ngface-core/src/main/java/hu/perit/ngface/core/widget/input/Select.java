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

package hu.perit.ngface.core.widget.input;

import hu.perit.ngface.core.widget.base.WidgetData;
import hu.perit.ngface.core.widget.input.validator.Required;
import hu.perit.ngface.core.widget.base.Input;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class Select extends Input<Select.Data, Void, Select>
{
    public Select(String id)
    {
        super(id);
    }


    @Override
    protected List<Class<?>> getAllowedValidators()
    {
        return Arrays.asList(Required.class);
    }

    @ToString
    @lombok.Data
    public static class Option
    {
        private final String id;
        private final String value;
    }

    @ToString(callSuper = true)
    @Getter
    public static class Data extends WidgetData
    {
        private final Map<String, String> options = new LinkedHashMap<>();
        private String selected;

        public Data addOption(Select.Option option)
        {
            this.options.put(option.id, option.value);
            return this;
        }


        public Data selected(String selected)
        {
            this.selected = selected;
            return this;
        }
    }
}
