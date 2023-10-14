/*
 * Copyright 2020-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package hu.perit.ngface.webservice.ngface.democomponent;

import hu.perit.ngface.core.controller.ComponentController;
import hu.perit.ngface.core.widget.input.DateRangeInput;
import hu.perit.ngface.core.widget.input.Select;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * @author Peter Nagy
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class DemoComponentController implements ComponentController<DemoComponentDTO, Long>
{
    @Override
    public DemoComponentDTO getForm(Long id)
    {
        // The data
        DemoComponentDTO data = new DemoComponentDTO();

        data.setOwnersName("Peter");
        data.setRole("Admin");
        data.setAmount(2.123456789);
        data.setCountSamples(10L);
        data.setCheckInDate(LocalDate.now());
        data.setDateRange(new DateRangeInput.Data(LocalDate.now(), LocalDate.now().plusDays(1)));
        data.setSelectData(new Select.Data()
            .addOption(new Select.Option("id_first", "First option"))
            .addOption(new Select.Option("id_second", "Second option")));
        data.setSelect2Data(new Select.Data()
            .addOption(new Select.Option("id_first", "First option"))
            .addOption(new Select.Option("id_second", "Second option")));
        data.setSelect3Data(new Select.Data()
            .addOption(new Select.Option("id_first", "First option"))
            .addOption(new Select.Option("id_second", "Second option")).selected("id_first"));

        return data;
    }


    @Override
    public void onFormSubmit(DemoComponentDTO data)
    {
        // Here you can save the submitted data
    }
}
