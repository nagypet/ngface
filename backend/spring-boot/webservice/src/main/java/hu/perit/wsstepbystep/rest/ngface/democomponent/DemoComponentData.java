package hu.perit.wsstepbystep.rest.ngface.democomponent;

import hu.perit.ngface.widget.input.TextInput;
import hu.perit.ngface.data.SubmitFormData;
import lombok.Data;

import java.util.List;

/**
 * This is the actual DTO object which have to be shown. This data object will be enriched with view information so that
 * the frontend receives all necessary information to render the widget.
 *
 * @author Peter Nagy
 */
@Data
public class DemoComponentData
{
    private String ownersName;
    private String placeOfBirth;
    private String email;
    private String role;
    private List<DemoTableDataProvider.DataRow> tableRows;
    private int totalTableRowCount;

    public void formSubmitted(SubmitFormData submitFormData)
    {
        this.ownersName = submitFormData.get(DemoComponentView.OWNERS_NAME_ID, TextInput.Data.class).getValue();
        this.placeOfBirth = submitFormData.get(DemoComponentView.PLACE_OF_BIRTH_ID, TextInput.Data.class).getValue();
        this.email = submitFormData.get(DemoComponentView.EMAIL_ID, TextInput.Data.class).getValue();
        this.role = submitFormData.get(DemoComponentView.ROLE_ID, TextInput.Data.class).getValue();
    }

}
