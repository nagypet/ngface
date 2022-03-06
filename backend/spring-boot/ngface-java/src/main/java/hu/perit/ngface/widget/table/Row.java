package hu.perit.ngface.widget.table;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@ToString
@Getter
public class Row
{
    private final String id;
    protected final List<String> cells = new ArrayList<>();

    // For JSon deserialization
    private Row()
    {
        this.id = null;
    }

    public Row addCell(String cellText)
    {
        this.cells.add(cellText);
        return this;
    }
}
