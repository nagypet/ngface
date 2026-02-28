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

package hu.perit.ngface.core.widget.input;

import hu.perit.ngface.core.widget.base.Input;
import hu.perit.ngface.core.widget.base.Value;
import hu.perit.ngface.core.widget.input.validator.Email;
import hu.perit.ngface.core.widget.input.validator.Pattern;
import hu.perit.ngface.core.widget.input.validator.Required;
import hu.perit.ngface.core.widget.input.validator.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class GenericAutocomplete<T extends AbstractOption> extends Input<GenericAutocomplete.Data<T>, T, GenericAutocomplete<T>>
{
    public GenericAutocomplete(String id)
    {
        super(id);
    }


    // Json
    private GenericAutocomplete()
    {
        super(null);
    }


    @Override
    protected Data<T> createDataFromSimpleValue(T value)
    {
        return new Data<>(value);
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
    public static class Data<T extends AbstractOption> extends Value<T>
    {
        private final ExtendedReadOnlyData<T> extendedReadOnlyData;


        // Json
        private Data()
        {
            super(null);
            this.extendedReadOnlyData = new ExtendedReadOnlyData<>();
        }


        public Data(T value)
        {
            super(value);
            this.extendedReadOnlyData = new ExtendedReadOnlyData<>();
        }


        public Data(T value, Boolean remote)
        {
            super(value);
            this.extendedReadOnlyData = new ExtendedReadOnlyData<>(remote);
        }


        // This part is only needed to render the widget, but will never be submitted back
        @lombok.Data
        public static class ExtendedReadOnlyData<T extends AbstractOption>
        {
            private final GenericValueSet<T> valueSet;


            public ExtendedReadOnlyData()
            {
                this.valueSet = new GenericValueSet<>(false);
            }


            public ExtendedReadOnlyData(Boolean remote)
            {
                this.valueSet = new GenericValueSet<>(remote);
            }


            public void options(Collection<T> options)
            {
                List<T> sortedOptions = options.stream().filter(i -> !i.isEmpty()).sorted(Comparator.nullsFirst(Comparator.naturalOrder())).toList();
                this.valueSet.values(sortedOptions);
            }
        }
    }
}
