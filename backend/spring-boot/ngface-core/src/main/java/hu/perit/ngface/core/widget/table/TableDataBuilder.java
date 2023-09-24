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
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TableDataBuilder
{
    private Table.Data data = new Table.Data();


    public static TableDataBuilder builder()
    {
        return new TableDataBuilder();
    }


    public TableDataBuilder paginator(Integer pageIndex, Integer pageSize, Long length, List<Integer> pageSizeOptions)
    {
        this.data.paginator(Paginator.of(pageIndex, pageSize, length, pageSizeOptions));
        return this;
    }


    public TableDataBuilder filterer(FiltererFactory factory)
    {
        factory.getFiltererDefMap().values().forEach(i -> this.data.addFilterer(factory.getFilterer(i.column(), "", true)));
        return this;
    }


    public Table.Data build()
    {
        return this.data;
    }
}
