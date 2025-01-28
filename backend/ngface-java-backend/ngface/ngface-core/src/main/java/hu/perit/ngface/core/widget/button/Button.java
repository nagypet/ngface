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

package hu.perit.ngface.core.widget.button;

import hu.perit.ngface.core.widget.base.VoidWidgetData;
import hu.perit.ngface.core.widget.base.Widget;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Button extends Widget<VoidWidgetData, Button>
{
    public static final Button OK = new Button("button-ok").label("OK").style(Style.PRIMARY);
    public static final Button CANCEL = new Button("button-cancel").label("Cancel");
    public static final Button SAVE = new Button("button-save").label("Save").style(Style.PRIMARY);
    public static final Button DELETE = new Button("button-delete").label("Delete").style(Style.WARN);

    public enum Style
    {
        NONE,
        PRIMARY,
        ACCENT,
        WARN
    }

    private Style style = Style.NONE;
    private String badge;

    public Button(String id)
    {
        super(id);
    }

    // Json
    private Button()
    {
        super(null);
    }

    public Button style(Style style)
    {
        this.style = style;
        return this;
    }

    public Button badge(String badge)
    {
        this.badge = badge;
        return this;
    }

    public Button badge(Long badge)
    {
        this.badge = badge != null ? String.valueOf(badge) : "";
        return this;
    }
}
