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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.BooleanUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@EqualsAndHashCode
public class ValueSet
{
    // If there are more then MAX_SIZE criteria, then they will not be provided, but must be searched for
    private static int MAX_SIZE = 100;

    // Remote means, the list of criteria is way too large, cannot be provided without a filter pattern. The client must
    // narrow down the list of distinct items by providing some filter patterns. The client must fetch the value set from
    // the backend each time the searchText changes.
    private Boolean remote;
    private Boolean truncated = Boolean.FALSE;
    private List<Item> values = new ArrayList<>();

    public ValueSet(Boolean remote)
    {
        this.remote = remote;
    }

    public ValueSet valueSet(Collection<String> valueSet)
    {
        this.values = new ArrayList<>();
        List<String> sortedValueSet = valueSet.stream().sorted(Comparator.nullsFirst(Comparator.naturalOrder())).collect(Collectors.toList());
        for (String value : sortedValueSet)
        {
            this.values.add(new Item().text(value));
            if (this.values.size() >= MAX_SIZE)
            {
                this.truncated = Boolean.TRUE;
                this.remote = Boolean.TRUE;
                break;
            }
        }
        return this;
    }

    public void selected(String text, Boolean value)
    {
        if (text == null)
        {
            return;
        }
        this.values.stream().filter(i -> text.equalsIgnoreCase(i.getText())).forEach(i -> i.selected(BooleanUtils.isTrue(value)));
    }

    @Getter
    @ToString
    @EqualsAndHashCode
    public static class Item
    {
        private String text;
        private Boolean selected = Boolean.TRUE;

        public Item text(String text)
        {
            this.text = text;
            return this;
        }

        public Item selected(Boolean selected)
        {
            this.selected = selected;
            return this;
        }
    }
}
