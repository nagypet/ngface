package hu.perit.ngface.widget.input.validator;

import hu.perit.ngface.widget.base.Validator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class Pattern extends Validator<Pattern>
{
    private final String pattern;

    public Pattern(String pattern, String message)
    {
        super(message);
        this.pattern = pattern;
    }
}
