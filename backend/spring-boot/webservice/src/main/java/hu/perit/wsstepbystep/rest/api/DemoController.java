package hu.perit.wsstepbystep.rest.api;

import hu.perit.ngface.data.ComponentDataProvider;
import hu.perit.ngface.data.SubmitFormData;
import hu.perit.ngface.widget.base.WidgetData;
import hu.perit.ngface.widget.form.Form;
import hu.perit.wsstepbystep.rest.ngface.democomponent.DemoComponentData;
import hu.perit.wsstepbystep.rest.ngface.democomponent.DemoComponentView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class DemoController implements DemoApi
{
    private final ComponentDataProvider<DemoComponentData> demoComponentDataProvider;

    //------------------------------------------------------------------------------------------------------------------
    // getDemoForm()
    //------------------------------------------------------------------------------------------------------------------
    @Override
    public Form getDemoForm(Long pageNumber, Long pageSize, String sortColumn, String sortDirection)
    {
        log.debug("getDemoForm(pageNumber: {}, pageSize: {}, sortColumn: {}, sortDirection: {})", pageNumber, pageSize, sortColumn, sortDirection);

        DemoComponentView view = new DemoComponentView(
                this.demoComponentDataProvider.getData(
                        new DemoComponentDataProviderParams(pageNumber, pageSize, sortColumn, sortDirection)));
        return view.getDemoForm();
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
        this.demoComponentDataProvider.setData(data);
    }

}
