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

package hu.perit.ngface.core.data;

import hu.perit.ngface.core.types.intf.SubmitFormData;
import hu.perit.ngface.core.types.table.TableDTO;
import hu.perit.ngface.core.widget.base.WidgetData;
import hu.perit.ngface.core.widget.exception.NgFaceBadRequestException;
import hu.perit.ngface.core.widget.input.DateInput;
import hu.perit.ngface.core.widget.input.NumericInput;
import hu.perit.ngface.core.widget.input.TextInput;
import hu.perit.ngface.core.widget.table.Table;
import hu.perit.spvitamin.core.reflection.ReflectionUtils;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Valid
public abstract class ComponentDTO
{
    public void formSubmitted(SubmitFormData submitFormData)
    {
        List<Field> properties = ReflectionUtils.propertiesOf(this.getClass(), true);

        for (Field property : properties)
        {
            handleDTOIdAnnotation(submitFormData, property);
            handleDTOValueAnnotation(submitFormData, property);
        }

        validate();

        log.debug(this.toString());
    }


    private void validate()
    {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory())
        {
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<ComponentDTO>> violations = validator.validate(this);
            if (!violations.isEmpty())
            {
                throw new ConstraintViolationException(violations);
            }
        }
    }


    private void handleDTOValueAnnotation(SubmitFormData submitFormData, Field property)
    {
        DTOValue dtoValueAnnotation = property.getAnnotation(DTOValue.class);
        if (dtoValueAnnotation != null)
        {
            String id = StringUtils.isNotBlank(dtoValueAnnotation.id()) ? dtoValueAnnotation.id() : property.getName();
            Class<? extends WidgetData> dataClass = getDataClass(dtoValueAnnotation, property);
            if (dataClass != null)
            {
                try
                {
                    WidgetData widgetData = submitFormData.get(id, dataClass);
                    Optional<Method> setter = ReflectionUtils.getSetter(this.getClass(), property.getName());
                    setter.ifPresent(method -> invokeSetter(method, widgetData));
                }
                catch (NgFaceBadRequestException ex)
                {
                    log.debug(ex.toString());
                }
            }
        }
    }


    private void handleDTOIdAnnotation(SubmitFormData submitFormData, Field property)
    {
        DTOId dtoIdAnnotation = property.getAnnotation(DTOId.class);
        if (dtoIdAnnotation != null)
        {
            Optional<Method> setter = ReflectionUtils.getSetter(this.getClass(), property.getName());
            setter.ifPresent(method -> invokeSetter(method, submitFormData.getId()));
        }
    }


    private void invokeSetter(Method setter, WidgetData widgetData)
    {
        Parameter[] parameters = setter.getParameters();
        Class<?> targetType = parameters[0].getType();

        try
        {
            Object convertedData = convertWidgetData(widgetData, targetType);
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


    private Object convertWidgetData(WidgetData widgetData, Class<?> targetType)
    {
        if (targetType.isAssignableFrom(widgetData.getClass()))
        {
            return widgetData;
        }

        // TextInput.Data
        if (widgetData.getClass().equals(TextInput.Data.class))
        {
            return convertTextInputData((TextInput.Data) widgetData, targetType);
        }

        // NumericInput.Data
        if (widgetData.getClass().equals(NumericInput.Data.class))
        {
            return convertNumericInputData((NumericInput.Data) widgetData, targetType);
        }

        // NumericInput.Data
        if (widgetData.getClass().equals(DateInput.Data.class))
        {
            return convertDateInputData((DateInput.Data) widgetData, targetType);
        }

        // Table.Data
        if (widgetData.getClass().equals(Table.Data.class))
        {
            return convertTableData((Table.Data) widgetData, targetType);
        }

        return null;
    }


    private Object convertTextInputData(TextInput.Data widgetData, Class<?> targetType)
    {
        if (String.class.equals(targetType))
        {
            return widgetData.getValue();
        }

        return null;
    }


    private Object convertNumericInputData(NumericInput.Data widgetData, Class<?> targetType)
    {
        if (BigDecimal.class.equals(targetType))
        {
            return widgetData.getValue();
        }

        if (Float.class.equals(targetType))
        {
            return widgetData.getValue().floatValue();
        }

        if (Double.class.equals(targetType))
        {
            return widgetData.getValue().doubleValue();
        }

        if (Long.class.equals(targetType) || long.class.equals(targetType))
        {
            return widgetData.getValue().longValue();
        }

        if (Integer.class.equals(targetType) || int.class.equals(targetType))
        {
            return widgetData.getValue().intValue();
        }

        return null;
    }


    private Object convertDateInputData(DateInput.Data widgetData, Class<?> targetType)
    {
        if (LocalDate.class.equals(targetType))
        {
            return widgetData.getValue();
        }

        if (LocalDateTime.class.equals(targetType))
        {
            return LocalDateTime.of(widgetData.getValue(), null);
        }

        return null;
    }


    private Object convertTableData(Table.Data widgetData, Class<?> targetType)
    {
        if (TableDTO.class.equals(targetType))
        {
            TableDTO<?> tableDTO = new TableDTO<>();
            tableDTO.setData(widgetData);
            return tableDTO;
        }

        return null;
    }


    private Class<? extends WidgetData> getDataClass(DTOValue dtoValueAnnotation, Field property)
    {
        if (!dtoValueAnnotation.type().equals(WidgetData.class))
        {
            return dtoValueAnnotation.type();
        }

        // calculating WidgetData class from the property type
        Class<?> type = property.getType();
        if (String.class.equals(type))
        {
            return TextInput.Data.class;
        }
        else if (Number.class.isAssignableFrom(type) || long.class.equals(type) || int.class.equals(type) || float.class.equals(type) || double.class.equals(
            type))
        {
            return NumericInput.Data.class;
        }
        else if (LocalDate.class.equals(type) || LocalDateTime.class.equals(type) || Date.class.equals(type) || ZonedDateTime.class.equals(type))
        {
            return DateInput.Data.class;
        }
        else if (TableDTO.class.isAssignableFrom(type))
        {
            return Table.Data.class;
        }
        else if (WidgetData.class.isAssignableFrom(type))
        {
            return (Class<? extends WidgetData>) type;
        }

        return null;
    }
}
