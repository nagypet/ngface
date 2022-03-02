package hu.perit.ngface.widget.input.validator;

import hu.perit.ngface.widget.base.Validator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class Max extends Validator<Max>
{
    private final Double max;

    public Max(Double max, String message)
    {
        super(message);
        this.max = max;
    }
}
