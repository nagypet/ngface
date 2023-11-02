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

package hu.perit.ngface.core.widget.titlebar;

import hu.perit.ngface.core.types.intf.Menu;
import hu.perit.ngface.core.widget.base.VoidWidgetData;
import hu.perit.ngface.core.widget.base.Widget;
import hu.perit.ngface.core.widget.table.Action;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Titlebar extends Widget<VoidWidgetData, Titlebar>
{
    private String appTitle = "App title";
    private Menu menu = new Menu();
    private List<Action> actions;

    public Titlebar(String id)
    {
        super(id);
    }

    public Titlebar appTitle(String appTitle)
    {
        this.appTitle = appTitle;
        return this;
    }


    public Titlebar menu(Menu menu)
    {
        this.menu = menu;
        return this;
    }


    public Titlebar actions(List<Action> actions)
    {
        this.actions = actions;
        return this;
    }
}
