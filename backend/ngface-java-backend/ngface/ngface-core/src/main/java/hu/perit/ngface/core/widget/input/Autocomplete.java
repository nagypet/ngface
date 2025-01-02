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

package hu.perit.ngface.core.widget.input;

import hu.perit.ngface.core.widget.base.Input;
import hu.perit.ngface.core.widget.base.Value;
import hu.perit.ngface.core.widget.input.validator.Email;
import hu.perit.ngface.core.widget.input.validator.Pattern;
import hu.perit.ngface.core.widget.input.validator.Required;
import hu.perit.ngface.core.widget.input.validator.Size;
import hu.perit.ngface.core.widget.table.ValueSet;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Autocomplete extends Input<Autocomplete.Data, String, Autocomplete>
{
    public Autocomplete(String id)
    {
        super(id);
    }


    // Json
    private Autocomplete()
    {
        super(null);
    }

    @Override
    protected Data createDataFromSimpleValue(String value)
    {
        return new Data(value);
    }


    @Override
    protected List<Class<?>> getAllowedValidators()
    {
        return List.of(Required.class, Size.class, Email.class, Pattern.class);
    }


    //-----------------------------------------------------------------------------------------------------------------
    // Data class
    //-----------------------------------------------------------------------------------------------------------------
    @ToString(callSuper = true)
    @Getter
    @EqualsAndHashCode(callSuper = true)
    public static class Data extends Value<String>
    {
        private final ExtendedReadOnlyData extendedReadOnlyData = new ExtendedReadOnlyData();

        // Json
        private Data()
        {
            super(null);
        }

        public Data(String value)
        {
            super(value);
        }


        // This part is only needed to render the widget, but will never be submitted back
        @lombok.Data
        public static class ExtendedReadOnlyData
        {
            private final ValueSet valueSet = new ValueSet(false);

            public void options(Collection<String> options)
            {
                List<String> sortedOptions = options.stream().filter(StringUtils::isNotBlank).sorted(Comparator.nullsFirst(Comparator.naturalOrder())).toList();
                this.valueSet.values(sortedOptions);
            }
        }
    }
}
