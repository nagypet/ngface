package hu.perit.ngface.widget.base;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import hu.perit.ngface.widget.input.DateInput;
import hu.perit.ngface.widget.input.DateTimeInput;
import hu.perit.ngface.widget.input.NumericInput;
import hu.perit.ngface.widget.input.TextInput;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * @author Peter Nagy
 */

@Getter
@RequiredArgsConstructor
@ToString
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonSubTypes({ //
        @JsonSubTypes.Type(value = TextInput.class, name = "TextInput"),
        @JsonSubTypes.Type(value = NumericInput.class, name = "NumericInput"),
        @JsonSubTypes.Type(value = DateInput.class, name = "DateInput"),
        @JsonSubTypes.Type(value = DateTimeInput.class, name = "DateTimeInput")
})
@EqualsAndHashCode
public abstract class Widget<SUB extends Widget>
{
    private final String type = getClass().getSimpleName();
    private final String id;
    protected String label;
    protected String hint;
    protected boolean enabled = true;

    // For JSon deserialization
    protected Widget()
    {
        this.id = null;
    }

    public SUB label(String label)
    {
        this.label = label;
        return (SUB) this;
    }

    public SUB hint(String hint)
    {
        this.hint = hint;
        return (SUB) this;
    }

    public SUB enabled(boolean enabled)
    {
        this.enabled = enabled;
        return (SUB) this;
    }
}
