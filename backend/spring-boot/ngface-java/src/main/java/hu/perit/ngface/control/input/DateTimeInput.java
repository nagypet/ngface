package hu.perit.ngface.control.input;

import hu.perit.ngface.base.Input;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author Peter Nagy
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(callSuper = true)
public class DateTimeInput extends Input<LocalDateTime, DateTimeInput>
{
    public DateTimeInput(String id)
    {
        super(id);
    }
}
