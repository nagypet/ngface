package hu.perit.ngface.widget.base;

import hu.perit.ngface.widget.exception.ValidatorNotAllowedException;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Peter Nagy
 */

@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
public abstract class Input<T, SUB extends Input> extends Widget<SUB>
{
    private String placeholder;
    private T value;
    private final List<Validator> validators = new ArrayList<>();

    public Input(String id)
    {
        super(id);
    }

    public SUB placeholder(String placeholder)
    {
        this.placeholder = placeholder;
        return (SUB) this;
    }

    public SUB value(T value)
    {
        this.value = value;
        return (SUB) this;
    }


    public SUB addValidator(Validator validator)
    {
        Objects.requireNonNull(validator, "validator may not be null'");

        if (!getAllowedValidators().contains(validator.getClass()))
        {
            throw new ValidatorNotAllowedException(String.format("'%s' does not allow validator of type '%s'!", getClass().getSimpleName(), validator.getClass().getSimpleName()));
        }

        this.validators.add(validator);
        return (SUB) this;
    }


    protected abstract List<Class<?>> getAllowedValidators();
}
