package hu.perit.ngface.data;

import hu.perit.ngface.widget.input.TextInput;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

class ComponentDTOTest
{
    @Test
    void testValidation()
    {
        SubmitFormData submitFormData = new SubmitFormData();
        submitFormData.setWidgetDataMap(new HashMap<>());
        submitFormData.getWidgetDataMap().put("ownersName", new TextInput.Data("Peter"));
        MyComponentDTO dto = new MyComponentDTO();
        dto.formSubmitted(submitFormData);
    }
}
