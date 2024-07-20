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

package hu.perit.ngface.core.widget.input.validator;

import hu.perit.ngface.core.widget.base.Validator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class Size extends Validator<Size>
{
    private int min = 0;
    private int max = Integer.MAX_VALUE;

    public Size(String message)
    {
        super(message);
    }

    public Size min(Integer min)
    {
        this.min = min != null ? min.intValue() : 0;
        return this;
    }

    public Size max(Integer max)
    {
        this.max = max != null ? max.intValue() : 0;
        return this;
    }
}
