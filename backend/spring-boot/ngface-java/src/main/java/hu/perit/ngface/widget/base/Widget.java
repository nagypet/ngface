package hu.perit.ngface.widget.base;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * @author Peter Nagy
 */

@Getter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public abstract class Widget<WD extends WidgetData, SUB extends Widget>
{
    private final String type = getClass().getSimpleName();
    private final String id;
    protected String label;
    protected String hint;
    protected boolean enabled = true;
    protected WD data;

    public SUB label(String label)
    {
        this.label = label;
        return (SUB) this;
    }

    public SUB hint(String hint)
    {
        this.hint = hint;
        return (SUB) this;
    }

    public SUB enabled(boolean enabled)
    {
        this.enabled = enabled;
        return (SUB) this;
    }

    public SUB data(WD data)
    {
        this.data = data;
        return (SUB) this;
    }
}
