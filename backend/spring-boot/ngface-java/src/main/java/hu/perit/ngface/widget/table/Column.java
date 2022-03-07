package hu.perit.ngface.widget.table;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@Getter
public class Column
{
    public enum Size
    {
        XS,
        S,
        M,
        L,
        XL
    }

    private final String id;
    private String text;
    private Boolean sortable = Boolean.FALSE;
    private Size size;

    // For JSon deserialization
    private Column()
    {
        this.id = null;
    }

    public Column text(String text)
    {
        this.text = text;
        return this;
    }

    public Column sortable(Boolean sortable)
    {
        this.sortable = sortable;
        return this;
    }

    public Column size(Size size)
    {
        this.size = size;
        return this;
    }
}
