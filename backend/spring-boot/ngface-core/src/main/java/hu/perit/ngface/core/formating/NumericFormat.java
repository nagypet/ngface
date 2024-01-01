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

package hu.perit.ngface.core.formating;

import lombok.Getter;

@Getter
public class NumericFormat extends AbstractFormat
{
    public static final NumericFormat PERCENTAGE = new NumericFormat().suffix("%");
    public static final NumericFormat UNGROUPED = new NumericFormat().digitGrouping(false);

    protected Integer precision;
    protected String prefix;
    protected String suffix;
    protected Boolean digitGrouping;

    public NumericFormat precision(Integer precision)
    {
        this.precision = precision;
        return this;
    }

    public NumericFormat prefix(String prefix)
    {
        this.prefix = prefix;
        return this;
    }

    public NumericFormat suffix(String suffix)
    {
        this.suffix = suffix;
        return this;
    }

    public NumericFormat digitGrouping(Boolean digitGrouping)
    {
        this.digitGrouping = digitGrouping;
        return this;
    }
}
