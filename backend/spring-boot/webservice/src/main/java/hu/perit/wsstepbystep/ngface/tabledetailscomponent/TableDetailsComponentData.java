package hu.perit.wsstepbystep.ngface.tabledetailscomponent;

import hu.perit.ngface.data.ComponentData;
import hu.perit.ngface.data.SubmitFormData;
import lombok.Data;

@Data
public class TableDetailsComponentData implements ComponentData
{
    private String id;
    private String name;
    private Double weight;
    private String symbol;

    @Override
    public void formSubmitted(SubmitFormData submitFormData)
    {
        this.id = submitFormData.getId();
        this.weight = submitFormData.getNumericInputValue(TableDetailsComponentView.WEIGHT).doubleValue();
        this.symbol = submitFormData.getTextInputValue(TableDetailsComponentView.SYMBOL);
    }
}
