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

package hu.perit.ngface.core.widget.form;

import hu.perit.ngface.core.widget.base.Widget;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Peter Nagy
 */

@Getter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public class Form
{
    private final String id;
    private String title;
    private final Map<String, Widget<?, ?>> widgets = new LinkedHashMap<>();

    // For JSon deserialization
    protected Form()
    {
        this.id = null;
    }

    public Form title(String title)
    {
        this.title = title;
        return this;
    }


    public Form addWidget(Widget<?, ?> widget)
    {
        this.widgets.put(widget.getId(), widget);
        return this;
    }
}
