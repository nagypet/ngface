package hu.perit.ngface.widget.base;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public abstract class Validator<SUB extends Validator>
{
    private final String type = getClass().getSimpleName();
    private final String message;
}
