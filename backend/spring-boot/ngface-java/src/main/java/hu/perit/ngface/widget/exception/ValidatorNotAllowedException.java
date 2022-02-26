package hu.perit.ngface.widget.exception;

public class ValidatorNotAllowedException extends RuntimeException
{
    public ValidatorNotAllowedException(String message)
    {
        super(message);
    }
}
