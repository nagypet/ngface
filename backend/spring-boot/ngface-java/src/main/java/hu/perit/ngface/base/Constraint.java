package hu.perit.ngface.base;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
@EqualsAndHashCode
public abstract class Constraint<SUB extends Constraint>
{
    private String message;

    public SUB message(String message)
    {
        this.message = message;
        return (SUB) this;
    }
}
