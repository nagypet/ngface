/*
 * Copyright 2020-2022 the original author or authors.
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

package hu.perit.wsstepbystep.ngface.tabledetailscomponent;

import hu.perit.ngface.data.ComponentData;
import hu.perit.ngface.data.SubmitFormData;
import lombok.Data;

@Data
public class TableDetailsComponentData implements ComponentData
{
    private String id;
    private String name;
    private Double weight;
    private String symbol;

    @Override
    public void formSubmitted(SubmitFormData submitFormData)
    {
        this.id = submitFormData.getId();
        this.weight = submitFormData.getNumericInputValue(TableDetailsComponentView.WEIGHT).doubleValue();
        this.symbol = submitFormData.getTextInputValue(TableDetailsComponentView.SYMBOL);
    }
}
