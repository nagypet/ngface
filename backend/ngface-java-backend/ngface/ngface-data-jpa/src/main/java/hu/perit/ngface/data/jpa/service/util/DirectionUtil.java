package hu.perit.ngface.data.jpa.service.util;

import hu.perit.ngface.core.types.intf.Direction;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Sort;

import java.util.Objects;

@UtilityClass
public class DirectionUtil
{
    public static Sort.Direction getJpaDirection(Direction direction)
    {
        return getJpaDirectionWithDefault(direction, Sort.Direction.ASC);
    }


    public static Sort.Direction getJpaDirectionWithDefault(Direction direction, Sort.Direction defaultDirection)
    {
        Objects.requireNonNull(defaultDirection);
        if (direction == null)
        {
            return defaultDirection;
        }
        return switch (direction)
        {
            case ASC -> Sort.Direction.ASC;
            case DESC -> Sort.Direction.DESC;
            default -> defaultDirection;
        };
    }
}
