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

package hu.perit.ngface.formating;

import lombok.Data;

@Data
public class FormatCurrency extends NumericFormat
{
    public static final NumericFormat EUR = new FormatCurrency("€");
    public static final NumericFormat USD = new FormatCurrency("$");
    public static final NumericFormat HUF = new FormatCurrency("Ft").precision(0);

    public FormatCurrency(String currency)
    {
        this.suffix = currency;
        this.precision = 2;
    }
}