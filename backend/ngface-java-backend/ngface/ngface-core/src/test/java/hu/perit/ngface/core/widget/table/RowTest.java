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

package hu.perit.ngface.core.widget.table;

import hu.perit.spvitamin.spring.json.JSonSerializer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class RowTest
{
    @Test
    void testInteger() throws IOException
    {
        Row<Integer> input = new Row<>(12);
        input.putCell("long-col", 1000L);
        input.putCell("string-col", "alma");

        String inputAsString = JSonSerializer.toJson(input);
        log.debug(inputAsString);
        Row<?> result = JSonSerializer.fromJson(inputAsString, Row.class);
        assertThat(result).isEqualTo(input);
    }


    @Test
    void testString() throws IOException
    {
        Row<String> input = new Row<>("row-id");
        input.putCell("long-col", 1000L);
        input.putCell("string-col", "alma");

        String inputAsString = JSonSerializer.toJson(input);
        log.debug(inputAsString);
        Row<?> result = JSonSerializer.fromJson(inputAsString, Row.class);
        assertThat(result).isEqualTo(input);
    }


    @Test
    void testLong() throws IOException
    {
        Row<Long> input = new Row<>(12L);
        input.putCell("long-col", 1000L);
        input.putCell("string-col", "alma");

        String inputAsString = JSonSerializer.toJson(input);
        log.debug(inputAsString);
        Row<?> result = JSonSerializer.fromJson(inputAsString, Row.class);
        assertThat(result).isEqualTo(input);
    }
}
