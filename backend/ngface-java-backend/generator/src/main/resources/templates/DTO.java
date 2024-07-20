package ${PACKAGE};

import hu.perit.ngface.data.ComponentDTO;
import hu.perit.ngface.data.DTOId;
import hu.perit.ngface.data.DTOValue;
import lombok.Data;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Size;

@Data
public class ${NAME}ComponentDTO extends ComponentDTO
{
    public static final String WEIGHT = "weight";
    public static final String SYMBOL = "symbol";

    // Id of the data row
    @DTOId
    private String id;

    // Name of the modal. Not annotated with @DTOValue because it will not be sumbitted by the frontend
    private String name;

    // Weight data element
    @DTOValue(id = WEIGHT)
    @DecimalMax("100.0")
    private Double weight;

    // Symbol data element
    @DTOValue(id = SYMBOL)
    @Size(min = 2, max = 10)
    private String symbol;
}
