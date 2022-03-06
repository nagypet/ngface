package hu.perit.ngface.widget.table;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.LinkedHashMap;
import java.util.Map;

@RequiredArgsConstructor
@ToString
@Getter
public class Row
{
    private final String id;
    protected final Map<String, String> cells = new LinkedHashMap<>();

    // For JSon deserialization
    private Row()
    {
        this.id = null;
    }

    public Row putCell(String colId, String text)
    {
        this.cells.put(colId, text);
        return this;
    }
}
