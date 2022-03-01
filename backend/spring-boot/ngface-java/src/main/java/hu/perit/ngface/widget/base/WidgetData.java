package hu.perit.ngface.widget.base;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import hu.perit.ngface.widget.input.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonSubTypes({ //
        @JsonSubTypes.Type(value = TextInput.Data.class, name = "TextInput.Data"),
        @JsonSubTypes.Type(value = NumericInput.Data.class, name = "NumericInput.Data"),
        @JsonSubTypes.Type(value = DateInput.Data.class, name = "DateInput.Data"),
        @JsonSubTypes.Type(value = DateTimeInput.Data.class, name = "DateTimeInput.Data"),
        @JsonSubTypes.Type(value = DateRangeInput.Data.class, name = "DateRangeInput.Data")
})
@EqualsAndHashCode
public class WidgetData
{
    private final String type = getTypeName();

    private String getTypeName()
    {
        String packageName = getClass().getPackage().getName();
        return getClass().getCanonicalName().substring(packageName.length() + 1);
    }
}
