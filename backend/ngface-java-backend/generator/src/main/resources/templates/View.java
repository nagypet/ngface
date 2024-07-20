package ${PACKAGE};

import hu.perit.ngface.view.ComponentView;
import hu.perit.ngface.widget.form.Form;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ${NAME}ComponentView implements ComponentView
{
//    public static final String WEIGHT = "weight";
//    public static final String SYMBOL = "symbol";

    private final ${NAME}ComponentDTO data;

    @Override
    public Form getForm()
    {
        return new Form("id");
    }
}
