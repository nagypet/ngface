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

package hu.perit.ngface.widget.input;

import hu.perit.ngface.formating.NumericFormat;
import hu.perit.ngface.widget.base.Widget;
import hu.perit.ngface.widget.input.validator.Min;
import hu.perit.ngface.widget.input.DateInput;
import hu.perit.ngface.widget.input.NumericInput;
import hu.perit.ngface.widget.input.TextInput;
import hu.perit.spvitamin.core.StackTracer;
import hu.perit.spvitamin.spring.json.JSonSerializer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;


@Slf4j
class WidgetTest
{

    @Test
    void testNumericInputWithFloat()
    {
        NumericInput originalControl = new NumericInput("amount")
                .enabled(true)
                .label("Amount")
                .hint("Amount")
                .placeholder("Type in the amount")
                .format(new NumericFormat().precision(2))
                .data(new NumericInput.Data(new BigDecimal("2.12")))
                .addValidator(new Min(0.0, "The smalles amount is 0 EUR"));

        try
        {
            String json = new JSonSerializer().toJson(originalControl);
            log.debug(json);
            //assertEquals("{\"@class\":\"hu.perit.ngface.control.input.NgFaceNumericInput\",\"id\":\"amount_input\",\"tooltip\":\"Amount input\",\"enabled\":true,\"placeholder\":\"Type in the amount\",\"value\":3.0}", json);
            Widget deserializedControl = JSonSerializer.fromJson(json, Widget.class);
            log.debug(deserializedControl.toString());
            assertTrue(originalControl.equals(deserializedControl));
        }
        catch (Exception e)
        {
            log.error(StackTracer.toString(e));
            fail();
        }
    }


    @Test
    void testNumericInputWithInteger()
    {
        NumericInput originalControl = new NumericInput("count-samples")
                .enabled(true)
                .label("Count of samples")
                .hint("Count of samples")
                .placeholder("Type in the count of samples, you want to print")
                .format(new NumericFormat().precision(0))
                .data(new NumericInput.Data(new BigDecimal("10")))
                .addValidator(new Min(0.0, "The count have to be above 0!"));

        try
        {
            String json = new JSonSerializer().toJson(originalControl);
            log.debug(json);
            //assertEquals("{\"@class\":\"hu.perit.ngface.control.input.NgFaceNumericInput\",\"id\":\"amount_input\",\"tooltip\":\"Amount input\",\"enabled\":true,\"placeholder\":\"Type in the amount\",\"value\":3.0}", json);
            Widget deserializedControl = JSonSerializer.fromJson(json, Widget.class);
            log.debug(deserializedControl.toString());
            assertTrue(originalControl.equals(deserializedControl));
        }
        catch (Exception e)
        {
            log.error(StackTracer.toString(e));
            fail();
        }
    }


    @Test
    void testDateInput()
    {
        DateInput originalControl = new DateInput("date")
                .enabled(true)
                .hint("Date input")
                .data(new DateInput.Data(LocalDate.of(2022, 2, 19)));

        try
        {
            String json = new JSonSerializer().toJson(originalControl);
            log.debug(json);
            //assertEquals("{\"@class\":\"hu.perit.ngface.control.input.NgFaceDateInput\",\"id\":\"date_input\",\"tooltip\":\"Date input\",\"enabled\":true,\"placeholder\":\"Effective date\",\"value\":\"2022-02-19\"}", json);
            Widget deserializedControl = JSonSerializer.fromJson(json, Widget.class);
            log.debug(deserializedControl.toString());
            assertTrue(originalControl.equals(deserializedControl));
        }
        catch (IOException e)
        {
            log.error(StackTracer.toString(e));
        }
    }
}
