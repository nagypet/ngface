package hu.perit.ngface.widget.input;

import hu.perit.ngface.widget.base.WidgetData;
import hu.perit.ngface.widget.base.Widget;
import hu.perit.spvitamin.core.StackTracer;
import hu.perit.spvitamin.spring.json.JSonSerializer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class TextInputTest
{

    @Test
    void testTextInput()
    {
        TextInput originalControl = new TextInput("name")
                .enabled(true)
                .hint("Name input")
                .placeholder("Type in your name!")
                .data(new TextInput.Data("Don Joe"));

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
    void testTextInputData()
    {
        TextInput.Data data = new TextInput.Data("some text");

        try
        {
            String json = new JSonSerializer().toJson(data);
            log.debug(json);
            WidgetData deserializedData = JSonSerializer.fromJson(json, WidgetData.class);
            log.debug(deserializedData.toString());
            assertTrue(data.equals(deserializedData));
        }
        catch (IOException e)
        {
            log.error(StackTracer.toString(e));
        }
    }

}
