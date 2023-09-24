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

package hu.perit.ngface.core.widget.formattedtext;

import hu.perit.ngface.core.widget.base.Value;
import hu.perit.ngface.core.widget.base.Widget;
import lombok.Getter;
import lombok.ToString;

public class FormattedText extends Widget<FormattedText.Data, FormattedText>
{
    public FormattedText(String id)
    {
        super(id);
    }

    public FormattedText html(String value)
    {
        this.data = new Data(value);
        return this;
    }

    @ToString(callSuper = true)
    @Getter
    public static class Data extends Value<String>
    {
        public Data(String value)
        {
            super(value);
        }

        // For JSon deserialization
        private Data()
        {
            super(null);
        }
    }
}
