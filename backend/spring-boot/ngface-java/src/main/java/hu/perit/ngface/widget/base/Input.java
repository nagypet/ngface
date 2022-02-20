package hu.perit.ngface.widget.base;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
    private final List<Constraint> constraints = new ArrayList<>();

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

    public SUB addConstraint(Constraint constraint)
    {
        this.constraints.add(constraint);
        return (SUB) this;
    }
}
