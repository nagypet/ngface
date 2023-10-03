package hu.perit.ngface.webservice.exceptionhandler;

import hu.perit.spvitamin.core.exception.AbstractMessageType;
import hu.perit.spvitamin.core.exception.LogLevel;
import hu.perit.spvitamin.spring.config.SpringContext;
import org.springframework.context.MessageSource;

import java.util.Locale;

public enum ApplicationMessage implements AbstractMessageType
{
    CONSTRAINT_VIOLATION_1                          (400, LogLevel.DEBUG),
    ONLY_EVEN_IDS_CAN_BE_EDITED                     (400, LogLevel.DEBUG)
    ;

    private final int httpStatusCode;
    private final LogLevel level;
    private Object[] params;

    ApplicationMessage(int statusCode, LogLevel level)
    {
        this.httpStatusCode = statusCode;
        this.level = level;
    }

    @Override
    public ApplicationMessage params(Object... params)
    {
        this.params = params;

        // Putting String type parameters onto single quotes
        if (this.params != null)
        {
            for (int i = 0; i < this.params.length; i++)
            {
                if (this.params[i] instanceof String && !((String) this.params[i]).startsWith("'"))
                {
                    this.params[i] = "'" + this.params[i] + "'";
                }
            }
        }

        return this;
    }


    @Override
    public int getHttpStatusCode()
    {
        return httpStatusCode;
    }


    @Override
    public LogLevel getLevel()
    {
        return level;
    }


    @Override
    public String getMessage()
    {
        MessageSource messageSource = SpringContext.getBean(MessageSource.class);
        return messageSource.getMessage(this.name(), params, Locale.getDefault());
    }
}
