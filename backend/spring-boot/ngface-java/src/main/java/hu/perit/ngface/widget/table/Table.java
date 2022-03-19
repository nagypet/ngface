package hu.perit.ngface.widget.table;

import hu.perit.ngface.widget.base.Widget;
import hu.perit.ngface.widget.base.WidgetData;
import hu.perit.ngface.widget.exception.NgFaceException;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Getter
@ToString(callSuper = true)
public class Table extends Widget<Table.Data, Table>
{
    public enum SelectMode
    {
        NONE,
        SINGLE,
        MULTI,
        CHECKBOX
    }

    private final Map<String, Column> columns = new LinkedHashMap<>();
    private final List<Row> rows = new ArrayList<>();
    private Paginator paginator;
    private SelectMode selectMode = SelectMode.NONE;

    public Table(String id)
    {
        super(id);
    }

    public Table addColumn(Column column)
    {
        if (!this.rows.isEmpty())
        {
            throw new NgFaceException("Please define columns before adding rows!");
        }

        this.columns.put(column.getId(), column);
        return this;
    }

    public Table addRow(Row row)
    {
        if (!this.columns.keySet().equals(row.getCells().keySet()))
        {
            throw new NgFaceException("The 'row' property contains uknkown column ids!");
        }

        this.rows.add(row);
        return this;
    }

    public Table paginator(Paginator paginator)
    {
        this.paginator = paginator;
        return this;
    }


    public Table selectMode(SelectMode selectMode)
    {
        this.selectMode = selectMode;
        return this;
    }

    @ToString(callSuper = true)
    @Getter
    public static class Data extends WidgetData
    {
    }
}
