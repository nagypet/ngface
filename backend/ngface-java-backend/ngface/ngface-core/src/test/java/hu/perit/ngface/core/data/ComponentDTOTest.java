/*
 * Copyright 2020-2025 the original author or authors.
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

package hu.perit.ngface.core.data;

import hu.perit.ngface.core.types.intf.SubmitFormData;
import hu.perit.ngface.core.widget.input.TextInput;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ComponentDTOTest
{
    @Test
    void testValidationOk()
    {
        SubmitFormData submitFormData = new SubmitFormData();
        submitFormData.setWidgetDataMap(new HashMap<>());
        submitFormData.getWidgetDataMap().put("ownersName", new TextInput.Data("Peter"));
        MyComponentDTO dto = new MyComponentDTO();
        assertThatCode(() -> dto.formSubmitted(submitFormData)).doesNotThrowAnyException();
    }


    @Test
    void testValidationNok()
    {
        SubmitFormData submitFormData = new SubmitFormData();
        submitFormData.setWidgetDataMap(new HashMap<>());
        submitFormData.getWidgetDataMap().put("ownersName", new TextInput.Data(null));
        MyComponentDTO dto = new MyComponentDTO();
        assertThatThrownBy(() -> dto.formSubmitted(submitFormData)).isInstanceOf(ConstraintViolationException.class);
    }
}
