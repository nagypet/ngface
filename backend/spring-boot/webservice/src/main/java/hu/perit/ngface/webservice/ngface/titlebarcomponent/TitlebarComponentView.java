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

package hu.perit.ngface.webservice.ngface.titlebarcomponent;

import hu.perit.ngface.core.types.intf.Menu;
import hu.perit.ngface.core.view.ComponentView;
import hu.perit.ngface.core.widget.form.Form;
import hu.perit.ngface.core.widget.table.Action;
import hu.perit.ngface.core.widget.titlebar.Titlebar;

import java.util.List;

public class TitlebarComponentView  implements ComponentView
{
    @Override
    public Form getForm()
    {
        return new Form("titlebar")
            .addWidget(new Titlebar("titlebar")
                .appTitle("ngface demo application")
                .version("2.2")
                .menu(new Menu()
                    .addItem(new Menu.Item("widgets_demo", "Widgets demo").icon("widgets"))
                    .addItem(new Menu.Item("table_demo", "Table demo").icon("table_view"))
                )
                .actions(List.of(
                    new Action("like").icon("favorite"),
                    new Action("share").icon("share")
                ))
            );
    }
}
