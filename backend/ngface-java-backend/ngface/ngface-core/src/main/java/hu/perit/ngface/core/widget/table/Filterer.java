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

package hu.perit.ngface.core.widget.table;

import lombok.*;
import org.apache.commons.lang3.BooleanUtils;

@ToString
@Getter
@RequiredArgsConstructor()
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@EqualsAndHashCode
public class Filterer
{
    private final String column;
    private ValueSet valueSet = new ValueSet(false);
    private String searchText = "";
    private Boolean active = Boolean.FALSE;

    public Filterer valueSet(ValueSet valueSet)
    {
        this.valueSet = valueSet;
        return this;
    }

    public Filterer searchText(String searchText)
    {
        this.searchText = searchText;
        return this;
    }

    public Filterer active(Boolean active)
    {
        this.active = BooleanUtils.isTrue(active);
        return this;
    }

    public void clear()
    {
        this.searchText = "";
        this.valueSet.getValues().forEach(i -> i.selected(true));
        this.active = Boolean.FALSE;
    }
}
