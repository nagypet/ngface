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

import hu.perit.ngface.core.widget.exception.NgFaceException;
import hu.perit.ngface.core.widget.input.validator.Required;
import hu.perit.ngface.core.widget.base.Input;
import hu.perit.ngface.core.widget.base.Value;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * @author Peter Nagy
 */

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DateInput extends Input<DateInput.Data, LocalDate, DateInput>
{
    public DateInput(String id)
    {
        super(id);
    }

    // Json
    private DateInput()
    {
        super(null);
    }

    @Override
    protected DateInput.Data createDataFromSimpleValue(LocalDate value)
    {
        return new Data(value);
    }

    @Override
    protected List<Class<?>> getAllowedValidators()
    {
        return Arrays.asList(Required.class);
    }

    @Override
    public DateInput placeholder(String placeholder)
    {
        throw new NgFaceException("Placeholder is not allowed on DateInput");
    }

    @ToString(callSuper = true)
    @Getter
    public static class Data extends Value<LocalDate>
    {
        public Data(LocalDate value)
        {
            super(value);
        }

        // For JSon deserialization
        private Data()
        {
            super(null);
        }
    }
}
