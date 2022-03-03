package hu.perit.ngface.widget.input;

import hu.perit.ngface.widget.base.Input;
import hu.perit.ngface.widget.base.WidgetData;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Select extends Input<Select.Data, Void, Select>
{
    public Select(String id)
    {
        super(id);
    }


    @Override
    protected List<Class<?>> getAllowedValidators()
    {
        return Collections.emptyList();
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
        private final List<Select.Option> options = new ArrayList<>();
        private String selected;

        public Data addOption(Select.Option option)
        {
            this.options.add(option);
            return this;
        }


        public Data selected(String selected)
        {
            this.selected = selected;
            return this;
        }
    }
}
