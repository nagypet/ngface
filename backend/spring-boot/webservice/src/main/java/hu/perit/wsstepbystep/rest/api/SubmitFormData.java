package hu.perit.wsstepbystep.rest.api;

import hu.perit.ngface.widget.base.WidgetData;
import lombok.Data;
import lombok.ToString;

import java.util.Map;

@Data
@ToString
public class SubmitFormData
{
    private Map<String, WidgetData> widgetDataMap;
}
