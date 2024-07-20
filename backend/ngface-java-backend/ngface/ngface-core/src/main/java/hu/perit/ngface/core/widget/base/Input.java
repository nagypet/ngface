/*
 * Copyright 2020-2024 the original author or authors.
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

import hu.perit.ngface.core.widget.exception.NgFaceException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Peter Nagy
 */

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public abstract class Input<WD extends WidgetData, V, SUB extends Input> extends Widget<WD, SUB>
{
    private String placeholder;
    private final List<Validator<?>> validators = new ArrayList<>();

    public Input(String id)
    {
        super(id);
    }

    protected WD createDataFromSimpleValue(V value)
    {
        throw new NgFaceException(String.format("value property is not allowed on '%s'!", getClass().getSimpleName()));
    }

    public SUB placeholder(String placeholder)
    {
        this.placeholder = placeholder;
        return (SUB) this;
    }

    public synchronized SUB value(V value)
    {
        this.data = createDataFromSimpleValue(value);
        return (SUB) this;
    }


    public SUB addValidator(Validator<?> validator)
    {
        Objects.requireNonNull(validator, "validator may not be null'");

        if (!getAllowedValidators().contains(validator.getClass()))
        {
            throw new NgFaceException(String.format("'%s' does not allow validator of type '%s'!", getClass().getSimpleName(), validator.getClass().getSimpleName()));
        }

        this.validators.add(validator);
        return (SUB) this;
    }


    protected abstract List<Class<?>> getAllowedValidators();
}
