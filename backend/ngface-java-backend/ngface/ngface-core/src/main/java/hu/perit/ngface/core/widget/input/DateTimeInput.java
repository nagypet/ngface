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

import hu.perit.ngface.core.widget.input.validator.Required;
import hu.perit.ngface.core.widget.base.Input;
import hu.perit.ngface.core.widget.base.Value;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * @author Peter Nagy
 */

@ToString(callSuper = true)
public class DateTimeInput extends Input<DateTimeInput.Data, LocalDateTime, DateTimeInput>
{
    public DateTimeInput(String id)
    {
        super(id);
    }

    @Override
    protected DateTimeInput.Data createDataFromSimpleValue(LocalDateTime value)
    {
        return new Data(value);
    }

    @Override
    protected List<Class<?>> getAllowedValidators()
    {
        return Arrays.asList(Required.class);
    }

    @ToString(callSuper = true)
    @Getter
    public static class Data extends Value<LocalDateTime>
    {
        public Data(LocalDateTime value)
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
