/*
 * Copyright (c) 2024. Innodox Technologies Zrt.
 * All rights reserved.
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
