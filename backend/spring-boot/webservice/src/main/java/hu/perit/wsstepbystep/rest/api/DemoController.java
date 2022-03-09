package hu.perit.wsstepbystep.rest.api;

import hu.perit.ngface.data.ComponentController;
import hu.perit.ngface.data.SubmitFormData;
import hu.perit.ngface.widget.base.WidgetData;
import hu.perit.ngface.widget.form.Form;
import hu.perit.wsstepbystep.ngface.democomponent.DemoComponentController;
import hu.perit.wsstepbystep.ngface.democomponent.DemoComponentData;
import hu.perit.wsstepbystep.ngface.democomponent.DemoComponentView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class DemoController implements DemoApi
{
    private final ComponentController<DemoComponentController.Params, DemoComponentData> demoComponentController;

    //------------------------------------------------------------------------------------------------------------------
    // getDemoForm()
    //------------------------------------------------------------------------------------------------------------------
    @Override
    public Form getDemoForm(Long pageNumber, Long pageSize, String sortColumn, String sortDirection)
    {
        DemoComponentController.Params params = new DemoComponentController.Params(pageNumber, pageSize, sortColumn, sortDirection);
        log.debug("getDemoForm({})", params);

        DemoComponentData data = this.demoComponentController.initializeData(params);
        return new DemoComponentView(data).getForm();
    }


    @Override
    public void submitDemoForm(SubmitFormData submitFormData)
    {
        log.debug("submitDemoForm()");

        if (!submitFormData.isEmpty())
        {
            for (Map.Entry<String, WidgetData> entry : submitFormData.entrySet())
            {
                log.debug(String.format("%s: %s", entry.getKey(), entry.getValue().toString()));
            }
        }

        DemoComponentData data = new DemoComponentData();
        data.formSubmitted(submitFormData);
        this.demoComponentController.onSave(data);
    }

}
