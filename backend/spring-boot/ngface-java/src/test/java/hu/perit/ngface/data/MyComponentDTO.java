package hu.perit.ngface.data;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class MyComponentDTO extends ComponentDTO
{
    @DTOValue(id = "ownersName")
    @NotNull
    private String ownersName;
}
