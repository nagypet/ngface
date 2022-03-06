package hu.perit.ngface.widget.table;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@Getter
public class Column
{
    private final String id;
    private String text;
    private Boolean sortable = Boolean.FALSE;

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
}
