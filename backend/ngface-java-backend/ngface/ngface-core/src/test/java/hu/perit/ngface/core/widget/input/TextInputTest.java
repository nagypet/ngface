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

package hu.perit.ngface.core.widget.input;

import hu.perit.ngface.core.widget.base.WidgetData;
import hu.perit.spvitamin.core.StackTracer;
import hu.perit.spvitamin.json.JSonSerializer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import tools.jackson.core.JacksonException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
class TextInputTest
{

    @Test
    void testTextInputData()
    {
        TextInput.Data data = new TextInput.Data("some text");

        try
        {
            String json = JSonSerializer.toJson(data);
            log.debug(json);
            WidgetData deserializedData = JSonSerializer.fromJson(json, WidgetData.class);
            log.debug(deserializedData.toString());
            assertEquals(data, deserializedData);
        }
        catch (JacksonException e)
        {
            log.error(StackTracer.toString(e));
        }
    }

}
