package ${PACKAGE};

import hu.perit.ngface.view.ComponentView;
import hu.perit.ngface.widget.form.Form;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class ${NAME}ComponentView implements ComponentView
{
//    public static final String WEIGHT = "weight";
//    public static final String SYMBOL = "symbol";

    private final ${NAME}ComponentData data;

    @Override
    public Form getForm()
    {
        return new Form("id");
    }
}
