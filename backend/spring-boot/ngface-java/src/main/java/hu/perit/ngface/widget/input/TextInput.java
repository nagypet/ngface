package hu.perit.ngface.widget.input;

import hu.perit.ngface.widget.base.Input;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
}
