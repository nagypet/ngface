package hu.perit.ngface.controller;

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
