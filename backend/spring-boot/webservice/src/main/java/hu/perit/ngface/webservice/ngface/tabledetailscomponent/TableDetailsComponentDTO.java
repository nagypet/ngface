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

package hu.perit.ngface.webservice.ngface.tabledetailscomponent;

import hu.perit.ngface.core.data.ComponentDTO;
import hu.perit.ngface.core.data.DTOId;
import hu.perit.ngface.core.data.DTOValue;
import hu.perit.ngface.webservice.model.AddressDTO;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class TableDetailsComponentDTO extends ComponentDTO
{
    // Id of the data row
    @DTOId
    @NotNull
    private String id;

    // Name of the modal. Not annotated with @DTOValue because it will not be sumbitted by the frontend
    private String name;

    @DTOValue(id = AddressDTO.COL_POSTCODE)
    @NotNull
    private Integer postCode;

    @DTOValue(id = AddressDTO.COL_CITY)
    @NotNull
    private String city;

    @DTOValue(id = AddressDTO.COL_STREET)
    @NotNull
    private String street;

    @DTOValue(id = AddressDTO.COL_DISTRICT)
    @NotNull
    private String district;
}
