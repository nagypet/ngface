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

package hu.perit.ngface.webservice.ngface.tabledetailscomponent;

import hu.perit.ngface.data.ComponentDTO;
import hu.perit.ngface.data.DTOId;
import hu.perit.ngface.data.DTOValue;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class TableDetailsComponentDTO extends ComponentDTO
{
    public static final String WEIGHT = "weight";
    public static final String SYMBOL = "symbol";

    // Id of the data row
    @DTOId
    @NotNull
    private String id;

    // Name of the modal. Not annotated with @DTOValue because it will not be sumbitted by the frontend
    private String name;

    // Weight data element
    @DTOValue(id = WEIGHT)
    @DecimalMax("100.0")
    @NotNull
    private Double weight;

    // Symbol data element
    @DTOValue(id = SYMBOL)
    @Size(min = 2, max = 10)
    @NotNull
    private String symbol;
}
