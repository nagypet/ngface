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
    public Table(String id)
    {
        super(id);
    }


    @ToString(callSuper = true)
    @Getter
    public static class Data extends WidgetData
    {
        private final List<Column> columns = new ArrayList<>();
        private final List<Row> rows = new ArrayList<>();
        private Paginator paginator;

        public Data addColumn(Column column)
        {
            this.columns.add(column);
            return this;
        }

        public Data addRow(Row row)
        {
            this.rows.add(row);
            return this;
        }

        public Data paginator(Paginator paginator)
        {
            this.paginator = paginator;
            return this;
        }
    }
}
