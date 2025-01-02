package hu.perit.ngface.core.widget.table;

import lombok.Data;

import java.util.function.BiFunction;

@Data
public class ParameterizedValueProvider<T, R> implements ValueProvider<T, R>
{
    private final String parameter;
    private final BiFunction<String, T, R> function;

    @Override
    public R apply(T filter)
    {
        return function.apply(parameter, filter);
    }
}
