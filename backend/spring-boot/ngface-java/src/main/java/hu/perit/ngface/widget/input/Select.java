package hu.perit.ngface.widget.input;

import hu.perit.ngface.widget.base.Input;
import hu.perit.ngface.widget.base.WidgetData;
import hu.perit.ngface.widget.input.validator.Required;
import lombok.Getter;
import lombok.ToString;

import java.util.*;

public class Select extends Input<Select.Data, Void, Select>
{
    public Select(String id)
    {
        super(id);
    }


    @Override
    protected List<Class<?>> getAllowedValidators()
    {
        return Arrays.asList(Required.class);
    }

    @ToString
    @lombok.Data
    public static class Option
    {
        private final String id;
        private final String value;
    }

    @ToString(callSuper = true)
    @Getter
    public static class Data extends WidgetData
    {
        private final Map<String, String> options = new LinkedHashMap<>();
        private String selected;

        public Data addOption(Select.Option option)
        {
            this.options.put(option.id, option.value);
            return this;
        }


        public Data selected(String selected)
        {
            this.selected = selected;
            return this;
        }
    }
}
