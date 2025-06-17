/*
 * Copyright (c) 2025. Innodox Technologies Zrt.
 * All rights reserved.
 */

package hu.perit.ngface.core.types.intf;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
@Getter
public enum ComparisonOperator
{
    @JsonProperty("=")
    EQ("="),
    @JsonProperty("<>")
    NEQ("<>"),
    @JsonProperty(">")
    GT(">"),
    @JsonProperty(">=")
    GTE(">="),
    @JsonProperty("<")
    LT("<"),
    @JsonProperty("<=")
    LTE("<="),
    IN("IN"),
    BETWEEN("BETWEEN"),
    LIKE("LIKE");

    private final String value;

    public static Optional<ComparisonOperator> fromValue(String input)
    {
        return Arrays.stream(ComparisonOperator.values()).filter(i -> i.getValue().equals(input)).findFirst();
    }
}
