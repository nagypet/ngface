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

package hu.perit.ngface.core.types.intf;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
public class Menu
{
    private final List<Item> items = new ArrayList<>();


    public Menu addItem(String id, String label)
    {
        this.items.add(new Item(id, label));
        return this;
    }

    public Menu addItem(Item item)
    {
        this.items.add(item);
        return this;
    }


    @Data
    public static class Item
    {
        private final String id;
        private final String label;
        private String icon;
        private Menu submenu;

        public Item icon(String icon)
        {
            this.icon = icon;
            return this;
        }


        public Item submenu(Menu submenu)
        {
            this.submenu = submenu;
            return this;
        }
    }
}
