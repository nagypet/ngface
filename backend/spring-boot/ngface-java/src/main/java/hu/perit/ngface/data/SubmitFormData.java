package hu.perit.ngface.data;

import hu.perit.ngface.widget.base.WidgetData;
import hu.perit.ngface.widget.exception.NgFaceBadRequestException;
import hu.perit.ngface.widget.input.DateInput;
import hu.perit.ngface.widget.input.NumericInput;
import hu.perit.ngface.widget.input.TextInput;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

@ToString
@Setter
public class SubmitFormData
{
    private Map<String, WidgetData> widgetDataMap;

    public <T extends WidgetData> T get(String id, Class<T> dataClass)
    {
        if (!this.widgetDataMap.containsKey(id))
        {
            throw new NgFaceBadRequestException(String.format("Widget id '%s' does not exist in submitted data!", id));
        }
        WidgetData widgetData = this.widgetDataMap.get(id);
        if (!widgetData.getClass().isAssignableFrom(dataClass))
        {
            throw new NgFaceBadRequestException(String.format("Widget type mismatch! Expected type: %s, actual type %s", dataClass.getSimpleName(), widgetData.getClass().getSimpleName()));
        }
        return (T) widgetData;
    }


    public String getTextInputValue(String id)
    {
        return get(id, TextInput.Data.class).getValue();
    }


    public BigDecimal getNumericInputValue(String id)
    {
        return get(id, NumericInput.Data.class).getValue();
    }


    public LocalDate getDateInputValue(String id)
    {
        return get(id, DateInput.Data.class).getValue();
    }


    public boolean isEmpty()
    {
        return this.widgetDataMap == null || this.widgetDataMap.isEmpty();
    }


    public Set<Map.Entry<String, WidgetData>> entrySet()
    {
        return widgetDataMap.entrySet();
    }
}
