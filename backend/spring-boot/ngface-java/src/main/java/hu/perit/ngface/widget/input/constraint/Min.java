package hu.perit.ngface.widget.input.constraint;

import hu.perit.ngface.widget.base.Constraint;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class Min extends Constraint<Min>
{
    private final Double min;

    // For JSon deserialization
    private Min()
    {
        super(null);
        this.min = null;
    }

    public Min(Double min, String message)
    {
        super(message);
        this.min = min;
    }
}
