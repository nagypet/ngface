package hu.perit.ngface.widget.input;

import hu.perit.ngface.widget.base.Input;
import hu.perit.ngface.widget.base.Validator;
import hu.perit.ngface.widget.input.validator.Email;
import hu.perit.ngface.widget.input.validator.Pattern;
import hu.perit.ngface.widget.input.validator.Required;
import hu.perit.ngface.widget.input.validator.Size;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;

/**
 * @author Peter Nagy
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(callSuper = true)
public class TextInput extends Input<String, TextInput>
{
    public TextInput(String id)
    {
        super(id);
    }

    @Override
    protected List<Class<?>> getAllowedValidators()
    {
        return Arrays.asList(Required.class, Size.class, Email.class, Pattern.class);
    }
}
