package hu.perit.ngface.widget.input;

import hu.perit.ngface.widget.base.Input;
import hu.perit.ngface.widget.base.Validator;
import hu.perit.ngface.widget.base.WidgetData;
import hu.perit.ngface.widget.exception.ValidatorNotAllowedException;
import hu.perit.ngface.widget.exception.ValueSetterNotAllowedException;
import hu.perit.ngface.widget.input.validator.Required;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.*;

@Getter
@ToString(callSuper = true)
public class DateRangeInput extends Input<DateRangeInput.Data, Void, DateRangeInput>
{
    private String placeholder2;
    private final List<Validator<?>> validators2 = new ArrayList<>();

    public DateRangeInput(String id)
    {
        super(id);
    }

    @Override
    protected DateRangeInput.Data createDataFromSimpleValue(Void value)
    {
        throw new ValueSetterNotAllowedException("DateRangeInput data requires two date values, please use the data property instead of value!");
    }

    @Override
    protected List<Class<?>> getAllowedValidators()
    {
        return Arrays.asList(Required.class);
    }


    public DateRangeInput placeholder2(String placeholder)
    {
        this.placeholder2 = placeholder;
        return this;
    }


    public DateRangeInput addValidator2(Validator validator)
    {
        Objects.requireNonNull(validator, "validator may not be null'");

        if (!getAllowedValidators().contains(validator.getClass()))
        {
            throw new ValidatorNotAllowedException(String.format("'%s' does not allow validator of type '%s'!", getClass().getSimpleName(), validator.getClass().getSimpleName()));
        }

        this.validators2.add(validator);
        return this;
    }


    @ToString(callSuper = true)
    @lombok.Data
    public static class Data extends WidgetData
    {
        private final LocalDate startDate;
        private final LocalDate endDate;
    }
}
