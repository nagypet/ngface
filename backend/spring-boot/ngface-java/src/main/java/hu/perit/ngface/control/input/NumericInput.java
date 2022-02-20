package hu.perit.ngface.control.input;

import hu.perit.ngface.base.Input;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @author Peter Nagy
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(callSuper = true)
public class NumericInput extends Input<BigDecimal, NumericInput>
{
    private int precision;

    public NumericInput(String id)
    {
        super(id);
    }

    public NumericInput precision(int precision)
    {
        this.precision = precision;
        return this;
    }
}
