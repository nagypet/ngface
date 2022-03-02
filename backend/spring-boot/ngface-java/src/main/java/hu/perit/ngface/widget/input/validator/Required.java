package hu.perit.ngface.widget.input.validator;

import hu.perit.ngface.widget.base.Validator;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode(callSuper = true)
public class Required extends Validator<Required>
{
    public Required(String message)
    {
        super(message);
    }
}
