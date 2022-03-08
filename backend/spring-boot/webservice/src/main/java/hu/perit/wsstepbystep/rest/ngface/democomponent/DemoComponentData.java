package hu.perit.wsstepbystep.rest.ngface.democomponent;

import hu.perit.ngface.data.SubmitFormData;
import hu.perit.ngface.widget.input.DateRangeInput;
import hu.perit.ngface.widget.input.Select;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
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
    private BigDecimal amount;
    private BigDecimal countSamples;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private DateRangeInput.Data dateRange;
    private Select.Data selectData;
    private Select.Data select2Data;
    private Select.Data select3Data;
    private List<DemoTableDataProvider.DataRow> tableRows;
    private int totalTableRowCount;

    public void formSubmitted(SubmitFormData submitFormData)
    {
        this.ownersName = submitFormData.getTextInputValue(DemoComponentView.OWNERS_NAME_ID);
        this.placeOfBirth = submitFormData.getTextInputValue(DemoComponentView.PLACE_OF_BIRTH_ID);
        this.email = submitFormData.getTextInputValue(DemoComponentView.EMAIL_ID);
        this.role = submitFormData.getTextInputValue(DemoComponentView.ROLE_ID);
        this.amount = submitFormData.getNumericInputValue(DemoComponentView.AMOUNT_ID);
        this.countSamples = submitFormData.getNumericInputValue(DemoComponentView.COUNT_SAMPLES_ID);
        this.checkInDate = submitFormData.getDateInputValue(DemoComponentView.CHECK_IN_DATE_ID);
        this.checkOutDate = submitFormData.getDateInputValue(DemoComponentView.CHECK_OUT_DATE_ID);
        this.dateRange = submitFormData.get(DemoComponentView.DATE_RANGE_ID, DateRangeInput.Data.class);
        this.selectData = submitFormData.get(DemoComponentView.SELECT_ID, Select.Data.class);
        this.select2Data = submitFormData.get(DemoComponentView.SELECT2_ID, Select.Data.class);
        this.select3Data = submitFormData.get(DemoComponentView.SELECT3_ID, Select.Data.class);
    }

}
