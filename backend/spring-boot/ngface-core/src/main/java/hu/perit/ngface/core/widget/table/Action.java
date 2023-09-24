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

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.BooleanUtils;

@RequiredArgsConstructor
@ToString
@Getter
public class Action
{
    private final String id;
    private String label;
    // See https://fonts.google.com/icons?selected=Material+Icons&icon.style=Outlined
    private String icon;
    private boolean enabled = true;

    public Action label(String label)
    {
        this.label = label;
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
}
