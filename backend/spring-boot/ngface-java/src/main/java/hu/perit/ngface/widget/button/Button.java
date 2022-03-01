package hu.perit.ngface.widget.button;

import hu.perit.ngface.widget.base.VoidWidgetData;
import hu.perit.ngface.widget.base.Widget;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(callSuper = true)
public class Button extends Widget<VoidWidgetData, Button>
{
    public static final Button OK = new Button("button-ok").label("OK").style(Style.PRIMARY);
    public static final Button CANCEL = new Button("button-cancel").label("Cancel");
    public static final Button SAVE = new Button("button-save").label("Save").style(Style.PRIMARY);
    public static final Button DELETE = new Button("button-delete").label("Delete").style(Style.WARN);

    public enum Style {
        NONE,
        PRIMARY,
        ACCENT,
        WARN
    }

    private Style style = Style.NONE;

    public Button(String id)
    {
        super(id);
    }

    public Button style(Style style)
    {
        this.style = style;
        return this;
    }
}
