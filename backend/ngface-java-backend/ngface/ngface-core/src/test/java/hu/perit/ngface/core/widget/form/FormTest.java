/*
 * Copyright (c) 2024. Innodox Technologies Zrt.
 * All rights reserved.
 */

package hu.perit.ngface.core.widget.form;

import hu.perit.ngface.core.widget.button.Button;
import hu.perit.ngface.core.widget.input.*;
import hu.perit.ngface.core.widget.table.Table;
import hu.perit.spvitamin.spring.json.JSonSerializer;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

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
        form.addWidget(new Table<Long>("table"));

        String json = JSonSerializer.toJson(form);
        Form result = JSonSerializer.fromJson(json, Form.class);
        assertThat(result).isEqualTo(form);
    }
}
