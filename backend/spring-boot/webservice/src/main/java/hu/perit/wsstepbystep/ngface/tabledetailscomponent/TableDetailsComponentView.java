package hu.perit.wsstepbystep.ngface.tabledetailscomponent;

import hu.perit.ngface.view.ComponentView;
import hu.perit.ngface.widget.button.Button;
import hu.perit.ngface.widget.form.Form;
import hu.perit.ngface.widget.input.NumericInput;
import hu.perit.ngface.widget.input.TextInput;
import hu.perit.ngface.widget.input.validator.Required;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class TableDetailsComponentView implements ComponentView
{
    public static final String WEIGHT = "weight";
    public static final String SYMBOL = "symbol";

    private final TableDetailsComponentData data;

    @Override
    public Form getForm()
    {
        return new Form(data.getId())
                .title(String.format("Details of %s", this.data.getName()))
                .addWidget(new TextInput(SYMBOL).value(this.data.getSymbol()).label("Symbol").addValidator(new Required("Symbol is required!")))
                .addWidget(new NumericInput(WEIGHT).value(BigDecimal.valueOf(this.data.getWeight())).label("Weight").suffix("g").addValidator(new Required("Weight is required!")))
                .addWidget(Button.OK)
                .addWidget(Button.CANCEL)
                ;
    }
}