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

import hu.perit.ngface.core.widget.exception.NgFaceException;
import hu.perit.ngface.core.widget.input.validator.Required;
import hu.perit.ngface.core.widget.base.Input;
import hu.perit.ngface.core.widget.base.Validator;
import hu.perit.ngface.core.widget.base.WidgetData;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DateRangeInput extends Input<DateRangeInput.Data, Void, DateRangeInput>
{
    private String placeholder2;
    private final List<Validator<?>> validators2 = new ArrayList<>();

    public DateRangeInput(String id)
    {
        super(id);
    }

    @Override
    protected List<Class<?>> getAllowedValidators()
    {
        return Arrays.asList(Required.class);
    }


    public DateRangeInput placeholder2(String placeholder)
    {
        this.placeholder2 = placeholder;
        return this;
    }


    public DateRangeInput addValidator2(Validator<?> validator)
    {
        Objects.requireNonNull(validator, "validator may not be null'");

        if (!getAllowedValidators().contains(validator.getClass()))
        {
            throw new NgFaceException(String.format("'%s' does not allow validator of type '%s'!", getClass().getSimpleName(), validator.getClass().getSimpleName()));
        }

        this.validators2.add(validator);
        return this;
    }


    @ToString(callSuper = true)
    @RequiredArgsConstructor
    @Getter
    @EqualsAndHashCode(callSuper = true)
    public static class Data extends WidgetData
    {
        private final LocalDate startDate;
        private final LocalDate endDate;

        // For JSon deserialization
        private Data()
        {
            this.startDate = null;
            this.endDate = null;
        }
    }
}
