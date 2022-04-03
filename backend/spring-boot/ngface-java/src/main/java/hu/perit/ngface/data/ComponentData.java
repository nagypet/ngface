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

package hu.perit.ngface.data;

import hu.perit.ngface.reflection.ReflectionUtils;
import hu.perit.ngface.widget.base.WidgetData;
import hu.perit.ngface.widget.input.NumericInput;
import hu.perit.ngface.widget.input.TextInput;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
public abstract class ComponentData
{
    public void formSubmitted(SubmitFormData submitFormData)
    {
        List<Field> properties = ReflectionUtils.propertiesOf(this.getClass(), true);

        for (Field property : properties)
        {
            handleWIdAnnotation(submitFormData, property);
            handleWDataAnnotation(submitFormData, property);
        }

        validate();

        log.debug(this.toString());
    }


    private void validate()
    {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<ComponentData>> violations = validator.validate(this);
        if (!violations.isEmpty())
        {
            throw new ConstraintViolationException(violations);
        }
    }


    private void handleWDataAnnotation(SubmitFormData submitFormData, Field property)
    {
        WData wDataAnnotation = property.getAnnotation(WData.class);
        if (wDataAnnotation != null)
        {
            String id = StringUtils.isNotBlank(wDataAnnotation.id()) ? wDataAnnotation.id() : property.getName();
            Class<? extends WidgetData> dataClass = getDataClass(wDataAnnotation, property);
            if (dataClass != null)
            {
                WidgetData widgetData = submitFormData.get(id, dataClass);
                Optional<Method> setter = ReflectionUtils.getSetter(this.getClass(), property.getName());
                setter.ifPresent(method -> invokeSetter(method, widgetData));
            }
        }
    }


    private void handleWIdAnnotation(SubmitFormData submitFormData, Field property)
    {
        WId wIdAnnotation = property.getAnnotation(WId.class);
        if (wIdAnnotation != null)
        {
            Optional<Method> setter = ReflectionUtils.getSetter(this.getClass(), property.getName());
            setter.ifPresent(method -> invokeSetter(method, submitFormData.getId()));
        }
    }


    private void invokeSetter(Method setter, WidgetData widgetData)
    {
        Parameter[] parameters = setter.getParameters();
        Class<?> type = parameters[0].getType();

        try
        {
            Object convertedData = convertWidgetData(widgetData, type);
            if (convertedData != null)
            {
                setter.invoke(this, convertedData);
            }
        }
        catch (IllegalAccessException | InvocationTargetException e)
        {
            log.error(e.toString());
        }
    }


    private void invokeSetter(Method setter, String id)
    {
        Parameter[] parameters = setter.getParameters();
        Class<?> type = parameters[0].getType();

        if (!String.class.equals(type))
        {
            log.error("The WId annotated field must be a String!");
            return;
        }

        try
        {
            setter.invoke(this, id);
        }
        catch (IllegalAccessException | InvocationTargetException e)
        {
            log.error(e.toString());
        }
    }


    private Object convertWidgetData(WidgetData widgetData, Class<?> type)
    {
        if (type.isAssignableFrom(widgetData.getClass()))
        {
            return widgetData;
        }

        // TextInput.Data
        if (widgetData.getClass().equals(TextInput.Data.class))
        {
            if (String.class.equals(type))
            {
                return ((TextInput.Data) widgetData).getValue();
            }
        }

        // NumericInput.Data
        if (widgetData.getClass().equals(NumericInput.Data.class))
        {
            if (BigDecimal.class.equals(type))
            {
                return ((NumericInput.Data) widgetData).getValue();
            }

            if (Float.class.equals(type))
            {
                return ((NumericInput.Data) widgetData).getValue().floatValue();
            }

            if (Double.class.equals(type))
            {
                return ((NumericInput.Data) widgetData).getValue().doubleValue();
            }

            if (Long.class.equals(type) || long.class.equals(type))
            {
                return ((NumericInput.Data) widgetData).getValue().longValue();
            }

            if (Integer.class.equals(type) || int.class.equals(type))
            {
                return ((NumericInput.Data) widgetData).getValue().intValue();
            }
        }

        return null;
    }


    private Class<? extends WidgetData> getDataClass(WData wDataAnnotation, Field property)
    {
        if (!wDataAnnotation.type().equals(WidgetData.class))
        {
            return wDataAnnotation.type();
        }

        // calculating WidgetData class from the property type
        Class<?> type = property.getType();
        if (String.class.equals(type))
        {
            return TextInput.Data.class;
        }
        else if (Number.class.isAssignableFrom(type) || long.class.equals(type) || int.class.equals(type) || float.class.equals(type) || double.class.equals(type))
        {
            return NumericInput.Data.class;
        }
        else if (WidgetData.class.isAssignableFrom(type))
        {
            return (Class<? extends WidgetData>) type;
        }

        return null;
    }
}
