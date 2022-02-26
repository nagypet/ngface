package hu.perit.ngface.widget.input.validator;

import hu.perit.ngface.widget.base.Validator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class Size extends Validator<Size>
{
    private int min = 0;
    private int max = Integer.MAX_VALUE;

    // For JSon deserialization
    private Size()
    {
        super(null);
    }


    public Size(String message)
    {
        super(message);
    }

    public Size min(Integer min)
    {
        this.min = min != null ? min.intValue() : 0;
        return this;
    }

    public Size max(Integer max)
    {
        this.max = max != null ? max.intValue() : 0;
        return this;
    }
}
