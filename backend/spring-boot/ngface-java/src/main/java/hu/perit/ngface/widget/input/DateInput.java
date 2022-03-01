package hu.perit.ngface.widget.input;

import hu.perit.ngface.widget.base.Input;
import hu.perit.ngface.widget.base.WidgetData;
import hu.perit.ngface.widget.exception.PropertyNotAllowedException;
import hu.perit.ngface.widget.input.validator.Required;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * @author Peter Nagy
 */

@ToString(callSuper = true)
public class DateInput extends Input<DateInput.Data, LocalDate, DateInput>
{
    public DateInput(String id)
    {
        super(id);
    }

    @Override
    protected DateInput.Data createDataFromSimpleValue(LocalDate value)
    {
        return new Data(value);
    }

    @Override
    protected List<Class<?>> getAllowedValidators()
    {
        return Arrays.asList(Required.class);
    }


    public DateInput placeholder(String placeholder)
    {
        throw new PropertyNotAllowedException("Placeholder is not allowed on DateInput");
    }

    @ToString(callSuper = true)
    @lombok.Data
    public static class Data extends WidgetData
    {
        private final LocalDate value;
    }
}
