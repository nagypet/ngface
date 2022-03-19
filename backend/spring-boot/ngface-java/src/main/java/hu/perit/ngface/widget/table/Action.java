package hu.perit.ngface.widget.table;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@Getter
public class Action
{
    private final String id;
    private String label;
    // See https://fonts.google.com/icons?selected=Material+Icons&icon.style=Outlined
    private String icon;

    public Action label(String label)
    {
        this.label = label;
        return this;
    }

    public Action icon(String icon)
    {
        this.icon = icon;
        return this;
    }
}
