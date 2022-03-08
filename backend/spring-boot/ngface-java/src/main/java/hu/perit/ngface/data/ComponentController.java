package hu.perit.ngface.data;

/**
 *
 * @param <T>
 * @author Peter Nagy
 */
public interface ComponentController<P, T>
{
    T initializeData(P params);
    void onSave(T data);
}
