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
import org.apache.commons.lang3.BooleanUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@ToString
@Getter
public class Filter
{
    private static int MAX_SIZE = 100;

    // If there are more then MAX_SIZE criteria, then they will not be provided, but must be searched for
    private Boolean remote = Boolean.FALSE;
    private List<String> criteria;

    public Filter remote(Boolean remote)
    {
        this.remote = BooleanUtils.isTrue(remote);
        return this;
    }


    public Filter criteria(Collection<String> filters)
    {
        this.criteria = new ArrayList<>();
        for (String filter : filters)
        {
            this.criteria.add(filter);
            if (this.criteria.size() >= MAX_SIZE)
            {
                break;
            }
        }
        return this;
    }
}
