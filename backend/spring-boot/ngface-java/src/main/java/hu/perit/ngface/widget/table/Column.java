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
}
