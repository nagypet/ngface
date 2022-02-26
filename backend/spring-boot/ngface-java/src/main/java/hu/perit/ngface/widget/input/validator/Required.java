package hu.perit.ngface.widget.input.validator;

import hu.perit.ngface.widget.base.Validator;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode(callSuper = true)
public class Required extends Validator<Required>
{
    // For JSon deserialization
    private Required()
    {
        super(null);
    }


    public Required(String message)
    {
        super(message);
    }
}
