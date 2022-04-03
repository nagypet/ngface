package ${PACKAGE};

import hu.perit.ngface.data.ComponentData;
import hu.perit.ngface.data.SubmitFormData;
import lombok.Data;

@Data
public class ${NAME}ComponentData implements ComponentData
{
//    private String id;
//    private String name;
//    private Double weight;
//    private String symbol;

    @Override
    public void formSubmitted(SubmitFormData submitFormData)
    {
    }
}
