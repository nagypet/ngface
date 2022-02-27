package hu.perit.ngface.widget.input;

import hu.perit.ngface.widget.base.Input;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Collections;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(callSuper = true)
public class DateRangeInput extends Input<Void, DateRangeInput>
{
    private DateRangeValue startDate;
    private DateRangeValue endDate;

    public DateRangeInput(String id)
    {
        super(id);
    }

    @Override
    protected List<Class<?>> getAllowedValidators()
    {
        return Collections.emptyList();
    }

    public DateRangeInput startDate(DateRangeValue startDate)
    {
        this.startDate = startDate;
        return this;
    }


    public DateRangeInput endDate(DateRangeValue endDate)
    {
        this.endDate = endDate;
        return this;
    }
}
