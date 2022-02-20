package hu.perit.ngface.widget.input.constraint;

import hu.perit.ngface.widget.base.Constraint;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode(callSuper = true)
public class Required extends Constraint<Required>
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
