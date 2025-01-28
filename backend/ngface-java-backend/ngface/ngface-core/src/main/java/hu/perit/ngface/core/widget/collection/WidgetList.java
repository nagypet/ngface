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

package hu.perit.ngface.core.widget.collection;

import hu.perit.ngface.core.widget.base.Widget;
import hu.perit.ngface.core.widget.base.WidgetData;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@EqualsAndHashCode(callSuper = true)
public class WidgetList extends Widget<WidgetList.Data, WidgetList>
{
    private final Map<String, Widget<?, ?>> widgets = new LinkedHashMap<>();

    public WidgetList(String id)
    {
        super(id);
    }

    public WidgetList addWidget(Widget<?,?> widget)
    {
        this.widgets.put(widget.getId(), widget);
        return this;
    }


    //-----------------------------------------------------------------------------------------------------------------
    // Data class
    //-----------------------------------------------------------------------------------------------------------------
    @ToString(callSuper = true)
    @Getter
    @EqualsAndHashCode(callSuper = true)
    public static class Data extends WidgetData
    {
        private final Map<String, WidgetData> widgetDataMap = new LinkedHashMap<>();
    }
}
