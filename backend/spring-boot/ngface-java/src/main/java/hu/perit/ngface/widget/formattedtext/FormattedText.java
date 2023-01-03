package hu.perit.ngface.widget.formattedtext;

import hu.perit.ngface.widget.base.Value;
import hu.perit.ngface.widget.base.Widget;
import lombok.Getter;
import lombok.ToString;

public class FormattedText extends Widget<FormattedText.Data, FormattedText>
{
    public FormattedText(String id)
    {
        super(id);
    }

    public FormattedText html(String value)
    {
        this.data = new Data(value);
        return this;
    }

    @ToString(callSuper = true)
    @Getter
    public static class Data extends Value<String>
    {
        public Data(String value)
        {
            super(value);
        }

        // For JSon deserialization
        private Data()
        {
            super(null);
        }
    }
}
