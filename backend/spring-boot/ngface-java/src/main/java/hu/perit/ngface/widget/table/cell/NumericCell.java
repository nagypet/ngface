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
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@EqualsAndHashCode(callSuper = true)
public class NumericCell extends Cell<BigDecimal, NumericCell>
{
    private NumericFormat format = new NumericFormat();

    public NumericCell(BigDecimal value)
    {
        super(value);
    }

    public NumericCell(Long value)
    {
        super(value != null ? new BigDecimal(value) : null);
    }

    public NumericCell format(NumericFormat format)
    {
        this.format = format;
        return this;
    }
}
