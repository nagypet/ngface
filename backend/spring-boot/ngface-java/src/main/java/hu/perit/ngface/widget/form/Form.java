package hu.perit.ngface.widget.form;

import hu.perit.ngface.widget.base.Widget;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Peter Nagy
 */

@Getter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public class Form
{
    private final String id;
    private final Map<String, Widget<?>> widgets = new HashMap<>();

    // For JSon deserialization
    protected Form()
    {
        this.id = null;
    }

    public Form addWidget(Widget<?> widget)
    {
        this.widgets.put(widget.getId(), widget);
        return this;
    }
}
