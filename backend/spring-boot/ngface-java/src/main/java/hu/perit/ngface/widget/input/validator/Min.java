package hu.perit.ngface.widget.input.validator;

import hu.perit.ngface.widget.base.Validator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class Min extends Validator<Min>
{
    private final Double min;

    public Min(Double min, String message)
    {
        super(message);
        this.min = min;
    }
}
