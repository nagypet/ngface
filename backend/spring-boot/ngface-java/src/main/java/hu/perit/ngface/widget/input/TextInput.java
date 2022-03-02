package hu.perit.ngface.widget.input;

import hu.perit.ngface.widget.base.Input;
import hu.perit.ngface.widget.base.Value;
import hu.perit.ngface.widget.base.WidgetData;
import hu.perit.ngface.widget.input.validator.Email;
import hu.perit.ngface.widget.input.validator.Pattern;
import hu.perit.ngface.widget.input.validator.Required;
import hu.perit.ngface.widget.input.validator.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;

/**
 * @author Peter Nagy
 */

@ToString(callSuper = true)
public class TextInput extends Input<TextInput.Data, String, TextInput>
{
    public TextInput(String id)
    {
        super(id);
    }

    @Override
    protected TextInput.Data createDataFromSimpleValue(String value)
    {
        return new Data(value);
    }

    @Override
    protected List<Class<?>> getAllowedValidators()
    {
        return Arrays.asList(Required.class, Size.class, Email.class, Pattern.class);
    }


    @ToString(callSuper = true)
    @Getter
    public static class Data extends Value<String>
    {
        public Data(String value)
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
