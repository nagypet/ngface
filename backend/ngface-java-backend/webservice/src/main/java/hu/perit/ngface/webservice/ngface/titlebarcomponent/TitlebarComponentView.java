/*
 * Copyright 2020-2024 the original author or authors.
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
import hu.perit.spvitamin.spring.manifest.ManifestReader;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Properties;

public class TitlebarComponentView implements ComponentView
{
    public static final String APP_TITLE = "ngface demo app";
    public static final String VERSION_100 = "1.0.0";

    @Override
    public Form getForm()
    {
        Properties manifest = ManifestReader.getManifestAttributes();
        String version = manifest.getProperty("Implementation-Version", "");
        String buildTime = manifest.getProperty("Build-Time", "");
        String build;
        if (StringUtils.isNoneBlank(buildTime))
        {
            build = String.format("%s", buildTime);
        }
        else
        {
            build = "Started from IDE, no build info is available!";
        }

        return new Form("titlebar")
                .addWidget(new Titlebar("titlebar")
                        .appTitle(APP_TITLE)
                        .version(StringUtils.isNotBlank(version) ? version : VERSION_100)
                        .buildTime(build)
                        .menu(new Menu()
                                .addItem(new Menu.Item("widgets_demo", "Widgets demo").icon("widgets"))
                                .addItem(new Menu.Item("table_demo", "Table demo").icon("table_view"))
                                .defaultItemId("widgets_demo")
                        )
                        .actions(List.of(
                                new Action("like").icon("favorite"),
                                new Action("share").icon("share")
                        ))
                );
    }
}
