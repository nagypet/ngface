package hu.perit.ngface.widget.base;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
@EqualsAndHashCode
@RequiredArgsConstructor
public abstract class Constraint<SUB extends Constraint>
{
    private final String message;
}
