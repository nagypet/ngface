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

package hu.perit.ngface.core.widget.base;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import hu.perit.ngface.core.widget.input.validator.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Email.class, name = "Email"),
        @JsonSubTypes.Type(value = Max.class, name = "Max"),
        @JsonSubTypes.Type(value = Min.class, name = "Min"),
        @JsonSubTypes.Type(value = Pattern.class, name = "Pattern"),
        @JsonSubTypes.Type(value = Required.class, name = "Required"),
        @JsonSubTypes.Type(value = Size.class, name = "Size")
})
public abstract class Validator
{
    private final String type = getClass().getSimpleName();
    private final String message;

    // Json
    private Validator()
    {
        this.message = null;
    }
}
