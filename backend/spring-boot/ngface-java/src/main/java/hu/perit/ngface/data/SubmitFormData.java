package hu.perit.ngface.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hu.perit.ngface.widget.base.WidgetData;
import hu.perit.ngface.widget.exception.NgFaceBadRequestException;
import hu.perit.ngface.widget.input.DateInput;
import hu.perit.ngface.widget.input.NumericInput;
import hu.perit.ngface.widget.input.TextInput;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Getter
@Setter
public class SubmitFormData
{
    private String id;
    private Map<String, WidgetData> widgetDataMap;

    @JsonIgnore
    public <T extends WidgetData> T get(String id, Class<T> dataClass)
    {
        if (this.widgetDataMap == null || !this.widgetDataMap.containsKey(id))
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


    @JsonIgnore
    public String getTextInputValue(String id)
    {
        return get(id, TextInput.Data.class).getValue();
    }


    @JsonIgnore
    public BigDecimal getNumericInputValue(String id)
    {
        return get(id, NumericInput.Data.class).getValue();
    }


    @JsonIgnore
    public LocalDate getDateInputValue(String id)
    {
        return get(id, DateInput.Data.class).getValue();
    }


    @JsonIgnore
    public boolean isEmpty()
    {
        return this.widgetDataMap == null || this.widgetDataMap.isEmpty();
    }


    @Override
    public String toString()
    {
        final StringBuffer sb = new StringBuffer("SubmitFormData{");
        sb.append("id='").append(id).append('\'');
        sb.append(", widgetDataMap=[");
        if (!isEmpty())
        {
            for (Map.Entry<String, WidgetData> entry : this.widgetDataMap.entrySet())
            {
                sb.append(String.format("\n\t'%s' => %s", entry.getKey(), entry.getValue().toString()));
            }
        }
        sb.append("]}");
        return sb.toString();
    }
}
