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

package hu.perit.ngface.widget.table.cell;

import hu.perit.ngface.formating.NumericFormat;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class NumericCell extends Cell<BigDecimal>
{
    private Integer precision;
    private String prefix;
    private String suffix;

    public NumericCell(BigDecimal value)
    {
        super(value);
    }

    public NumericCell precision(Integer precision)
    {
        this.precision = precision;
        return this;
    }

    public NumericCell prefix(String prefix)
    {
        this.prefix = prefix;
        return this;
    }

    public NumericCell suffix(String suffix)
    {
        this.suffix = suffix;
        return this;
    }

    public NumericCell format(NumericFormat format)
    {
        this.precision = format.getPrecision();
        this.suffix = format.getSuffix();
        this.prefix = format.getPrefix();
        return this;
    }
}
