package hu.perit.ngface.control;

import hu.perit.ngface.base.Widget;
import hu.perit.ngface.constraints.Min;
import hu.perit.ngface.control.input.DateInput;
import hu.perit.ngface.control.input.NumericInput;
import hu.perit.ngface.control.input.TextInput;
import hu.perit.spvitamin.core.StackTracer;
import hu.perit.spvitamin.spring.json.JSonSerializer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;


@Slf4j
class WidgetTest
{

    @Test
    void testTextInput()
    {
        TextInput originalControl = new TextInput("name_input")
                .enabled(true)
                .tooltip("Name input")
                .placeholder("Type in your name!")
                .value("Don Joe");

        try
        {
            String json = new JSonSerializer().toJson(originalControl);
            log.debug(json);
            //assertEquals("{\"@class\":\"hu.perit.ngface.control.input.NgFaceTextInput\",\"id\":\"name_input\",\"tooltip\":\"Name input\",\"enabled\":true,\"placeholder\":\"Type in your name!\",\"value\":\"Peter Nagy\"}", json);
            Widget deserializedControl = JSonSerializer.fromJson(json, Widget.class);
            log.debug(deserializedControl.toString());
            assertTrue(originalControl.equals(deserializedControl));
        }
        catch (IOException e)
        {
            log.error(StackTracer.toString(e));
        }
    }


    @Test
    void testNumericInput()
    {
        NumericInput originalControl = new NumericInput("amount_input");
        originalControl
                .enabled(true)
                .tooltip("Amount input")
                .placeholder("Type in the amount")
                .precision(2)
                .value(new BigDecimal("2.12"))
                .addConstraint(new Min(0.0, "The smalles amount is 0 EUR"));

        try
        {
            String json = new JSonSerializer().toJson(originalControl);
            log.debug(json);
            //assertEquals("{\"@class\":\"hu.perit.ngface.control.input.NgFaceNumericInput\",\"id\":\"amount_input\",\"tooltip\":\"Amount input\",\"enabled\":true,\"placeholder\":\"Type in the amount\",\"value\":3.0}", json);
            Widget deserializedControl = JSonSerializer.fromJson(json, Widget.class);
            log.debug(deserializedControl.toString());
            assertTrue(originalControl.equals(deserializedControl));
        }
        catch (IOException e)
        {
            log.error(StackTracer.toString(e));
        }
    }


    @Test
    void testDateInput()
    {
        DateInput originalControl = new DateInput("date_input");
        originalControl
                .enabled(true)
                .tooltip("Date input")
                .placeholder("Effective date")
                .value(LocalDate.of(2022, 2, 19));

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
