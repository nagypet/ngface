package hu.perit.ngface.constraints;

import hu.perit.ngface.base.Constraint;
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
    protected Min()
    {
        this.min = null;
    }

    public Min(Double min, String message)
    {
        message(message);
        this.min = min;
    }
}
