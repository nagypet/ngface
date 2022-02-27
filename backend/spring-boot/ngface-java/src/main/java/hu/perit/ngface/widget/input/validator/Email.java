package hu.perit.ngface.widget.input.validator;

import hu.perit.ngface.widget.base.Validator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class Email extends Validator<Email>
{
    // For JSon deserialization
    private Email()
    {
        super(null);
    }


    public Email(String message)
    {
        super(message);
    }
}