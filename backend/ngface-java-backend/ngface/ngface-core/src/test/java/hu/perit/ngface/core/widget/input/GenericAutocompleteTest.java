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

import hu.perit.spvitamin.core.StackTracer;
import hu.perit.spvitamin.spring.json.JSonSerializer;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

@Slf4j
class GenericAutocompleteTest
{
    @Test
    void testGenericAutocompleteData_SerializationDeserialization()
    {
        GenericAutocomplete.Data<DataOption> data = new GenericAutocomplete.Data<>(new DataOption("1", "Charlie", "1118 Budapest"), Boolean.TRUE);
        data.getExtendedReadOnlyData().options(Arrays.asList(
                new DataOption("1", "Charlie", "1118 Budapest"),
                new DataOption("2", "Alpha", "3201 Szeged")
        ));

        try
        {
            String json = JSonSerializer.toJson(data);
            log.debug(json);

            GenericAutocomplete.Data<?> deserialized = JSonSerializer.fromJson(json, GenericAutocomplete.Data.class);
            log.debug(deserialized.toString());

            assertNotNull(deserialized);
            assertEquals(data, deserialized);

            // Check the extended read-only data gets initialized and remote flag is preserved
            assertNotNull(deserialized.getExtendedReadOnlyData());
            assertNotNull(deserialized.getExtendedReadOnlyData().getValueSet());
            assertEquals(Boolean.TRUE, deserialized.getExtendedReadOnlyData().getValueSet().getRemote());
        }
        catch (IOException e)
        {
            log.error(StackTracer.toString(e));
            fail(e);
        }
    }


    @Test
    void testExtendedReadOnlyDataOptions_FilterAndSort()
    {
        GenericAutocomplete.Data.ExtendedReadOnlyData<DataOption> ext = new GenericAutocomplete.Data.ExtendedReadOnlyData<>(false);

        List<DataOption> input = Arrays.asList(
                new DataOption("1", "Charlie", "1118 Budapest"),
                new DataOption("", "", ""), // blank -> should be filtered out
                new DataOption("2", "Alpha", "3201 Szeged"),
                new DataOption(null, null, null) // null -> should be filtered out
        );

        ext.options(input);

        GenericValueSet<DataOption> valueSet = ext.getValueSet();
        assertNotNull(valueSet);
        assertEquals(Boolean.FALSE, valueSet.getRemote());
        assertEquals(2, valueSet.getValues().size());

        // Values should be sorted by text (Alpha, Charlie)
        assertEquals("Alpha", valueSet.getValues().get(0).getValue().getTexts().getFirst());
        assertEquals("Charlie", valueSet.getValues().get(1).getValue().getTexts().getFirst());
    }


    @Getter
    @ToString
    @EqualsAndHashCode
    public static class DataOption implements AbstractOption
    {
        private String id;
        private final List<String> texts = new ArrayList<>();


        // For Jackson
        public DataOption()
        {
        }


        public DataOption(String id, String... text)
        {
            this.id = id;
            this.texts.addAll(Arrays.asList(text));
        }


        @Override
        public int compareTo(@NotNull AbstractOption o)
        {
            if (!(o instanceof DataOption other))
            {
                return -1;
            }
            return new CompareToBuilder()
                    .append(this.getTexts().getFirst(), other.getTexts().getFirst())
                    .toComparison();
        }
    }
}
