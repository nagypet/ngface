package hu.perit.ngface.widget.table;

import hu.perit.ngface.widget.base.Widget;
import hu.perit.ngface.widget.base.WidgetData;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString(callSuper = true)
public class Table extends Widget<Table.Data, Table>
{
    private final List<Column> columns = new ArrayList<>();
    private final List<Row> rows = new ArrayList<>();
    private Paginator paginator;

    public Table(String id)
    {
        super(id);
    }

    public Table addColumn(Column column)
    {
        this.columns.add(column);
        return this;
    }

    public Table addRow(Row row)
    {
        this.rows.add(row);
        return this;
    }

    public Table paginator(Paginator paginator)
    {
        this.paginator = paginator;
        return this;
    }


    @ToString(callSuper = true)
    @Getter
    public static class Data extends WidgetData
    {
    }
}
