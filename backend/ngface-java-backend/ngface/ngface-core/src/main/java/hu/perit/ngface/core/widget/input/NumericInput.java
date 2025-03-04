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

import hu.perit.ngface.core.formating.NumericFormat;
import hu.perit.ngface.core.widget.input.validator.Max;
import hu.perit.ngface.core.widget.input.validator.Min;
import hu.perit.ngface.core.widget.input.validator.Required;
import hu.perit.ngface.core.widget.base.Input;
import hu.perit.ngface.core.widget.base.Value;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * @author Peter Nagy
 */

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class NumericInput extends Input<NumericInput.Data, BigDecimal, NumericInput>
{
    private NumericFormat format = new NumericFormat();

    public NumericInput(String id)
    {
        super(id);
    }


    // Json
    private NumericInput()
    {
        super(null);
    }

    @Override
    protected NumericInput.Data createDataFromSimpleValue(BigDecimal value)
    {
        return new Data(value);
    }

    @Override
    protected List<Class<?>> getAllowedValidators()
    {
        return Arrays.asList(Required.class, Min.class, Max.class);
    }

    public NumericInput format(NumericFormat format)
    {
        this.format = format;
        return this;
    }

    public NumericInput value(Integer value)
    {
        this.value(value != null ? BigDecimal.valueOf(value) : null);
        return this;
    }

    public NumericInput value(Long value)
    {
        this.value(value != null ? BigDecimal.valueOf(value) : null);
        return this;
    }

    public NumericInput value(Float value)
    {
        this.value(value != null ? BigDecimal.valueOf(value) : null);
        return this;
    }

    public NumericInput value(Double value)
    {
        this.value(value != null ? BigDecimal.valueOf(value) : null);
        return this;
    }

    @ToString(callSuper = true)
    @Getter
    public static class Data extends Value<BigDecimal>
    {
        // For JSon deserialization
        public Data(BigDecimal value)
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
