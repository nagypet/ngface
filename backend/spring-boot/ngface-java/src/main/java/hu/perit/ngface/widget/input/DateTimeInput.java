package hu.perit.ngface.widget.input;

import hu.perit.ngface.widget.base.Input;
import hu.perit.ngface.widget.base.WidgetData;
import hu.perit.ngface.widget.input.validator.Required;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * @author Peter Nagy
 */

@ToString(callSuper = true)
public class DateTimeInput extends Input<DateTimeInput.Data, LocalDateTime, DateTimeInput>
{
    public DateTimeInput(String id)
    {
        super(id);
    }

    @Override
    protected DateTimeInput.Data createDataFromSimpleValue(LocalDateTime value)
    {
        return new Data(value);
    }

    @Override
    protected List<Class<?>> getAllowedValidators()
    {
        return Arrays.asList(Required.class);
    }

    @ToString(callSuper = true)
    @lombok.Data
    public static class Data extends WidgetData
    {
        private final LocalDateTime value;
    }
}
