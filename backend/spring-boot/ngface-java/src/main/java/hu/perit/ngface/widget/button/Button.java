package hu.perit.ngface.widget.button;

import hu.perit.ngface.widget.base.Widget;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(callSuper = true)
public class Button extends Widget<Button>
{
    public static final Button OK = new Button("button-ok").label("OK");
    public static final Button CANCEL = new Button("button-cancel").label("Cancel");
    public static final Button SAVE = new Button("button-save").label("Save");

    public Button(String id)
    {
        super(id);
    }
}
