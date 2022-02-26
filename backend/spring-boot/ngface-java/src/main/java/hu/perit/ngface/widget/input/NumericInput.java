package hu.perit.ngface.widget.input;

import hu.perit.ngface.widget.base.Input;
import hu.perit.ngface.widget.input.validator.Max;
import hu.perit.ngface.widget.input.validator.Min;
import hu.perit.ngface.widget.input.validator.Required;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * @author Peter Nagy
 */

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(callSuper = true)
public class NumericInput extends Input<BigDecimal, NumericInput>
{
    private int precision;

    public NumericInput(String id)
    {
        super(id);
    }

    @Override
    protected List<Class<?>> getAllowedValidators()
    {
        return Arrays.asList(Required.class, Min.class, Max.class);
    }

    public NumericInput precision(int precision)
    {
        this.precision = precision;
        return this;
    }
}
