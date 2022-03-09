package hu.perit.ngface.widget.table;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.BooleanUtils;

import java.util.LinkedHashMap;
import java.util.Map;

@RequiredArgsConstructor
@ToString
@Getter
public class Row
{
    private final String id;
    private final Map<String, String> cells = new LinkedHashMap<>();
    private boolean selected;

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

    public Row selected(Boolean selected)
    {
        this.selected = BooleanUtils.isTrue(selected);
        return this;
    }
}
