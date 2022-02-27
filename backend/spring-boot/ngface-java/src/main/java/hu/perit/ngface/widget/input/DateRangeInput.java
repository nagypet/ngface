package hu.perit.ngface.widget.input;

import hu.perit.ngface.widget.base.Input;
import hu.perit.ngface.widget.input.validator.Required;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(callSuper = true)
public class DateRangeInput extends Input<LocalDate, DateRangeInput>
{
    private LocalDate endDate;

    public DateRangeInput(String id)
    {
        super(id);
    }

    @Override
    protected List<Class<?>> getAllowedValidators()
    {
        return Arrays.asList(Required.class);
    }


    public DateRangeInput startDate(LocalDate startDate)
    {
        this.value(startDate);
        return this;
    }


    public DateRangeInput endDate(LocalDate endDate)
    {
        this.endDate = endDate;
        return this;
    }
}
