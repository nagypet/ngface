package hu.perit.ngface.widget.table.cell;

import lombok.Data;

@Data
public abstract class Cell<V>
{
    private final String type = getClass().getSimpleName();
    protected final V value;
}
