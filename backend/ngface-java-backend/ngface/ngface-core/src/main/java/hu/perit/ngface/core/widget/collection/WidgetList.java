package hu.perit.ngface.core.widget.collection;

import hu.perit.ngface.core.widget.base.Widget;
import hu.perit.ngface.core.widget.base.WidgetData;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@EqualsAndHashCode(callSuper = true)
public class WidgetList extends Widget<WidgetList.Data, WidgetList>
{
    private final Map<String, Widget<?, ?>> widgets = new LinkedHashMap<>();

    public WidgetList(String id)
    {
        super(id);
    }

    public WidgetList addWidget(Widget<?,?> widget)
    {
        this.widgets.put(widget.getId(), widget);
        return this;
    }


    //-----------------------------------------------------------------------------------------------------------------
    // Data class
    //-----------------------------------------------------------------------------------------------------------------
    @ToString(callSuper = true)
    @Getter
    @EqualsAndHashCode(callSuper = true)
    public static class Data extends WidgetData
    {
        private final Map<String, WidgetData> widgetDataMap = new LinkedHashMap<>();
    }
}
