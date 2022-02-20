package hu.perit.ngface.widget.base;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import hu.perit.ngface.widget.input.constraint.Min;
import hu.perit.ngface.widget.input.constraint.Required;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Locale;

@Getter
@ToString
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonSubTypes({ //
        @JsonSubTypes.Type(value = Min.class, name = "Min"),
        @JsonSubTypes.Type(value = Required.class, name = "Required")
})
@EqualsAndHashCode
@RequiredArgsConstructor
public abstract class Constraint<SUB extends Constraint>
{
    private final String type = getClass().getSimpleName();
    private final String message;
}
