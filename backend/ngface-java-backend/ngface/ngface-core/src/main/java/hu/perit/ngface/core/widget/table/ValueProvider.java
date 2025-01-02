package hu.perit.ngface.core.widget.table;

@FunctionalInterface
public interface ValueProvider<T, R>
{
    R apply(T filter);
}
