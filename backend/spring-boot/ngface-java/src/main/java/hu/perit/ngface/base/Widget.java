package hu.perit.ngface.base;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
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
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
@EqualsAndHashCode
public abstract class Widget<SUB extends Widget>
{
    private final String id;
    protected String tooltip;
    protected boolean enabled = true;

    // For JSon deserialization
    protected Widget()
    {
        this.id = null;
    }

    public SUB tooltip(String tooltip)
    {
        this.tooltip = tooltip;
        return (SUB) this;
    }

    public SUB enabled(boolean enabled)
    {
        this.enabled = enabled;
        return (SUB) this;
    }
}
