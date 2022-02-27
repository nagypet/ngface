package hu.perit.ngface.widget.input;

import hu.perit.ngface.widget.base.Validator;
import hu.perit.ngface.widget.exception.ValidatorNotAllowedException;
import hu.perit.ngface.widget.input.validator.Required;
import lombok.*;

import java.time.LocalDate;
import java.util.*;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode()
public class DateRangeValue
{
    private String placeholder;
    private LocalDate value;
    private final List<Validator> validators = new ArrayList<>();


    public DateRangeValue placeholder(String placeholder)
    {
        this.placeholder = placeholder;
        return this;
    }


    public DateRangeValue value(LocalDate value)
    {
        this.value = value;
        return this;
    }


    public DateRangeValue addValidator(Validator validator)
    {
        Objects.requireNonNull(validator, "validator may not be null'");

        if (!getAllowedValidators().contains(validator.getClass()))
        {
            throw new ValidatorNotAllowedException(String.format("'%s' does not allow validator of type '%s'!", getClass().getSimpleName(), validator.getClass().getSimpleName()));
        }

        this.validators.add(validator);
        return this;
    }


    private List<Class<?>> getAllowedValidators()
    {
        return Arrays.asList(Required.class);
    }
}
