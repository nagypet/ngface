package hu.perit.ngface.core.widget.input;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "type")
public interface AbstractOption extends Comparable<AbstractOption>
{
    default String getType()
    {
        return this.getClass().getName();
    }

    String getId();

    // For multi-line items
    @NotNull List<String> getTexts();

    @JsonIgnore
    default boolean isEmpty()
    {
        return getTexts().stream().allMatch(i -> StringUtils.isBlank(i));
    }
}
