package hu.perit.ngface.widget.base;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import hu.perit.ngface.widget.input.validator.Min;
import hu.perit.ngface.widget.input.validator.Required;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonSubTypes({ //
        @JsonSubTypes.Type(value = Min.class, name = "Min"),
        @JsonSubTypes.Type(value = Required.class, name = "Required")
})
@EqualsAndHashCode
@RequiredArgsConstructor
public abstract class Validator<SUB extends Validator>
{
    private final String type = getClass().getSimpleName();
    private final String message;
}
