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

package hu.perit.ngface.core.formating;

import hu.perit.spvitamin.core.util.CurrencyConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CurrencyFormat extends NumericFormat
{
    public static final NumericFormat EUR = new CurrencyFormat("€");
    public static final NumericFormat USD = new CurrencyFormat("$");
    public static final NumericFormat HUF = new CurrencyFormat("Ft").precision(0);


    public CurrencyFormat(String currencySymbol)
    {
        this.suffix = currencySymbol;
        this.precision = 2;
    }


    public static NumericFormat fromCurrencyCode(String currencyCode, NumericFormat defaultFormat)
    {
        if (currencyCode == null)
        {
            return defaultFormat;
        }
        NumericFormat format = switch (currencyCode)
        {
            case "EUR" -> EUR;
            case "USD" -> USD;
            case "HUF" -> HUF;
            default -> null;
        };
        if (format != null)
        {
            return format;
        }
        String standardSymbol = CurrencyConverter.getStandardSymbol(currencyCode);
        return standardSymbol != null ? new CurrencyFormat(standardSymbol) : defaultFormat;
    }
}
