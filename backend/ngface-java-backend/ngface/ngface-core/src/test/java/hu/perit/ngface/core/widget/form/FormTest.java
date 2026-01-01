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

package hu.perit.ngface.core.widget.form;

import hu.perit.ngface.core.widget.button.Button;
import hu.perit.ngface.core.widget.input.Autocomplete;
import hu.perit.ngface.core.widget.input.DateInput;
import hu.perit.ngface.core.widget.input.DateRangeInput;
import hu.perit.ngface.core.widget.input.NumericInput;
import hu.perit.ngface.core.widget.input.Select;
import hu.perit.ngface.core.widget.input.TextInput;
import hu.perit.ngface.core.widget.table.Table;
import hu.perit.spvitamin.json.JSonSerializer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class FormTest
{
    @Test
    void test() throws IOException
    {
        Form form = new Form();
        form.addWidget(new Button("button_ok"));
        form.addWidget(new TextInput("text-input"));
        form.addWidget(new NumericInput("numeric-input"));
        form.addWidget(new DateInput("date-input"));
        form.addWidget(new DateRangeInput("date-range-input"));
        form.addWidget(new Select("select"));
        form.addWidget(new Autocomplete("autocomplete"));

        String json = JSonSerializer.toJson(form);
        log.debug(json);
        Form result = JSonSerializer.fromJson(json, Form.class);
        assertThat(result).isEqualTo(form);
    }

    @Test
    void testTable() throws IOException
    {
        Form form = new Form();
        form.addWidget(new Table<Long>("table"));

        String json = JSonSerializer.toJson(form);
        log.debug(json);
        Form result = JSonSerializer.fromJson(json, Form.class);
        assertThat(result).isEqualTo(form);
    }
}
