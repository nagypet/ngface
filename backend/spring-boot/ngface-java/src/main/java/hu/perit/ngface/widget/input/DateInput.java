package hu.perit.ngface.widget.input;

import hu.perit.ngface.widget.base.Input;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

/**
 * @author Peter Nagy
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(callSuper = true)
public class DateInput extends Input<LocalDate, DateInput>
{

    public DateInput(String id)
    {
        super(id);
    }
}
