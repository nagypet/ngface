package hu.perit.ngface.core.widget.table;

import hu.perit.ngface.core.widget.base.VoidWidgetData;
import hu.perit.ngface.core.widget.base.Widget;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@EqualsAndHashCode(callSuper = true)
public class FiltererGroup extends Widget<VoidWidgetData, FiltererGroup> implements FiltererPanelItem
{
    @Setter(AccessLevel.NONE)
    private List<FiltererPanelItem> items = new ArrayList<>();


    public FiltererGroup(String id)
    {
        super(id);
    }


    // Json
    private FiltererGroup()
    {
        super(null);
    }


    public FiltererGroup add(FiltererPanelItem item)
    {
        if (item != null)
        {
            this.items.add(item);
        }
        return this;
    }


    @Override
    public Long getCountActiveFilters()
    {
        return this.items.stream().mapToLong(FiltererPanelItem::getCountActiveFilters).sum();
    }
}
