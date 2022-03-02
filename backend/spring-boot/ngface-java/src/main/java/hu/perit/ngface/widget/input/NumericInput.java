package hu.perit.ngface.widget.input;

import hu.perit.ngface.widget.base.Input;
import hu.perit.ngface.widget.base.Value;
import hu.perit.ngface.widget.input.validator.Max;
import hu.perit.ngface.widget.input.validator.Min;
import hu.perit.ngface.widget.input.validator.Required;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * @author Peter Nagy
 */

@Getter
@ToString(callSuper = true)
public class NumericInput extends Input<NumericInput.Data, BigDecimal, NumericInput>
{
    private int precision;
    private String prefix;
    private String suffix;

    public NumericInput(String id)
    {
        super(id);
    }

    @Override
    protected NumericInput.Data createDataFromSimpleValue(BigDecimal value)
    {
        return new Data(value);
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

    public NumericInput prefix(String prefix)
    {
        this.prefix = prefix;
        return this;
    }

    public NumericInput suffix(String suffix)
    {
        this.suffix = suffix;
        return this;
    }

    @ToString(callSuper = true)
    @Getter
    public static class Data extends Value<BigDecimal>
    {
        // For JSon deserialization
        public Data(BigDecimal value)
        {
            super(value);
        }

        // For JSon deserialization
        private Data()
        {
            super(null);
        }
    }
}
