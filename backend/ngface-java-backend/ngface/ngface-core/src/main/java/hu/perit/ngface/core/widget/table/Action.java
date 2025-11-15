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

import jakarta.annotation.Nullable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.BooleanUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@ToString
@Getter
@EqualsAndHashCode
public class Action
{
    public enum Style
    {
        ICON,
        BUTTON,
        ACTION_GROUP,
        LINK
    }

    public enum TextPlacement
    {
        HIDDEN,
        BEFORE,
        AFTER
    }


    private final String id;
    private String label;
    private String text;
    private TextPlacement textPlacement = TextPlacement.HIDDEN;
    // See https://fonts.google.com/icons?selected=Material+Icons&icon.style=Outlined
    private String icon;
    private boolean enabled = true;
    private String badge;
    private Style style = Style.ICON;
    @Setter(AccessLevel.NONE)
    @Nullable
    private List<Action> actions;


    public Action()
    {
        this.id = null;
    }


    public Action label(String label)
    {
        this.label = label;
        return this;
    }


    public Action text(String text)
    {
        this.text = text;
        return this;
    }


    public Action textPlacement(TextPlacement textPlacement)
    {
        this.textPlacement = textPlacement;
        return this;
    }


    public Action icon(String icon)
    {
        this.icon = icon;
        return this;
    }


    public Action enabled(Boolean enabled)
    {
        this.enabled = BooleanUtils.isTrue(enabled);
        return this;
    }


    public Action badge(String badge)
    {
        this.badge = badge;
        return this;
    }


    public Action style(Style style)
    {
        this.style = style;
        return this;
    }


    public synchronized Action addAction(Action action)
    {
        if (action != null)
        {
            if (this.actions == null)
            {
                this.actions = new ArrayList<>();
            }
            this.actions.add(action);
        }
        return this;
    }


    public Action actions(List<Action> actions)
    {
        if (actions != null)
        {
            this.actions = new ArrayList<>(actions);
        }
        return this;
    }


    public List<Action> getActions()
    {
        return actions != null ? Collections.unmodifiableList(actions) : null;
    }
}
