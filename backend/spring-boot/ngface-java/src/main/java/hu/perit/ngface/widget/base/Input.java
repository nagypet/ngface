package hu.perit.ngface.widget.base;

import hu.perit.ngface.widget.exception.NgFaceException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Peter Nagy
 */

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public abstract class Input<WD extends WidgetData, V, SUB extends Input> extends Widget<WD, SUB>
{
    private String placeholder;
    private final List<Validator<?>> validators = new ArrayList<>();

    public Input(String id)
    {
        super(id);
    }

    protected WD createDataFromSimpleValue(V value)
    {
        throw new NgFaceException(String.format("value property is not allowed on '%s'!", getClass().getSimpleName()));
    }

    public SUB placeholder(String placeholder)
    {
        this.placeholder = placeholder;
        return (SUB) this;
    }

    public synchronized SUB value(V value)
    {
        this.data = createDataFromSimpleValue(value);
        return (SUB) this;
    }


    public SUB addValidator(Validator validator)
    {
        Objects.requireNonNull(validator, "validator may not be null'");

        if (!getAllowedValidators().contains(validator.getClass()))
        {
            throw new NgFaceException(String.format("'%s' does not allow validator of type '%s'!", getClass().getSimpleName(), validator.getClass().getSimpleName()));
        }

        this.validators.add(validator);
        return (SUB) this;
    }


    protected abstract List<Class<?>> getAllowedValidators();
}
