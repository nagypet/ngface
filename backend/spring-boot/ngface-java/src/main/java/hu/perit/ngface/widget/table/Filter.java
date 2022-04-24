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

package hu.perit.ngface.widget.table;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@ToString
@Getter
public class Filter
{
    private static int MAX_SIZE = 100;

    private List<String> filters;

    public Filter filters(Collection<String> filters)
    {
        this.filters = new ArrayList<>();
        for (String filter : filters)
        {
            this.filters.add(filter);
            if (this.filters.size() >= MAX_SIZE)
            {
                break;
            }
        }
        return this;
    }
}
