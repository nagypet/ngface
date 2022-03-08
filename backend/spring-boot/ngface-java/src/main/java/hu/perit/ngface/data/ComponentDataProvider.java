package hu.perit.ngface.data;

/**
 *
 * @param <T>
 * @author Peter Nagy
 */
public interface ComponentDataProvider<T>
{
    T getData(DataProviderParams params);
    void setData(T data);
}
