package hu.perit.ngface.core.widget.table;

import hu.perit.ngface.core.widget.base.VoidWidgetData;
import hu.perit.ngface.core.widget.base.Widget;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Getter
public class FiltererPanel extends Widget<VoidWidgetData, FiltererPanel>
{
    private final List<FiltererPanelItem> items = new ArrayList<>();


    public FiltererPanel(String id)
    {
        super(id);
    }


    // Json
    private FiltererPanel()
    {
        super(null);
    }


    public FiltererPanel add(FiltererPanelItem item)
    {
        if (item != null)
        {
            this.items.add(item);
        }
        return this;
    }


    public Long getCountActiveFilters()
    {
        return this.items.stream().mapToLong(FiltererPanelItem::getCountActiveFilters).sum();
    }
}
