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

package hu.perit.ngface.core.widget.table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FiltererFactory
{
    public record FiltererDef(String column, Boolean remote, Function<String, List<String>> valueProvider)
    {
    }

    private final Map<String, FiltererDef> filtererDefMap = new HashMap<>();


    public static FiltererFactory builder()
    {
        return new FiltererFactory();
    }


    public FiltererFactory filterer(String column, Boolean remote, Function<String, List<String>> valueProvider)
    {
        this.filtererDefMap.put(column, new FiltererDef(column, remote, valueProvider));
        return this;
    }


    public Filterer getFilterer(String column, String searchText, boolean skipRemote)
    {
        if (!this.filtererDefMap.containsKey(column))
        {
            throw new IllegalStateException(String.format("Invalid column specified '%s'!", column));
        }

        FiltererDef filtererDef = this.filtererDefMap.get(column);

        return new Filterer(column).valueSet(getValueSet(searchText, skipRemote, filtererDef)).searchText(searchText);
    }


    private ValueSet getValueSet(String searchText, Boolean skipRemote, FiltererDef filtererDef)
    {
        ValueSet valueSet = new ValueSet(filtererDef.remote());
        // At the initialization phase there is no need to provide real values for remote filterer, because they will be filled on-demand as the user
        // clicks the filter icon on the column header.
        if (!(BooleanUtils.isTrue(skipRemote) && BooleanUtils.isTrue(filtererDef.remote())))
        {
            valueSet.values(filtererDef.valueProvider.apply(searchText));
        }
        return valueSet;
    }
}
