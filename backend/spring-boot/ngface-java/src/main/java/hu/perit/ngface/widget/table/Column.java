/*
 * Copyright 2020-2022 the original author or authors.
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

package hu.perit.ngface.widget.table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@ToString
@Getter
public class Column
{
    public enum Size
    {
        AUTO,
        XS,
        S,
        M,
        L,
        XL,
        TIMESTAMP,
        NUMBER
    }

    public enum TextAlign
    {
        LEFT,
        CENTER,
        RIGHT
    }

    private final String id;
    private String text;
    private Boolean sortable = Boolean.FALSE;
    private Size size = Size.AUTO;
    private TextAlign textAlign = TextAlign.LEFT;

    public Column text(String text)
    {
        this.text = text;
        return this;
    }

    public Column sortable(Boolean sortable)
    {
        this.sortable = sortable;
        return this;
    }

    public Column size(Size size)
    {
        this.size = size;
        return this;
    }

    public Column textAlign(TextAlign textAlign)
    {
        this.textAlign = textAlign;
        return this;
    }
}
