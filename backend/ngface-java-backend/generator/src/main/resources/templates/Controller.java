package ${PACKAGE};

import hu.perit.ngface.controller.ComponentController;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ${NAME}ComponentController implements ComponentController<${NAME}ComponentController.Params, ${NAME}ComponentDTO>
{
    @Data
    public static class Params
    {
        private final Long id;
    }


    @Override
    public ${NAME}ComponentDTO initializeData(Params params)
    {
        ${NAME}ComponentDTO data = new ${NAME}ComponentDTO();
        return data;
    }


    @Override
    public void onSave(${NAME}ComponentDTO data)
    {
    }
}
