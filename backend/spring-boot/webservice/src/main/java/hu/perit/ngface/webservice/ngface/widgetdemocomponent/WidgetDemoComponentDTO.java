/*
 * Copyright 2020-2024 the original author or authors.
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

package hu.perit.ngface.webservice.ngface.widgetdemocomponent;

import hu.perit.ngface.core.data.ComponentDTO;
import hu.perit.ngface.core.data.DTOValue;
import hu.perit.ngface.core.widget.input.Autocomplete;
import hu.perit.ngface.core.widget.input.DateRangeInput;
import hu.perit.ngface.core.widget.input.Select;
import lombok.Data;

import java.time.LocalDate;

/**
 * This is the actual DTO object which have to be shown. This data object will be enriched with view information so that
 * the frontend receives all necessary information to render the widget.
 *
 * @author Peter Nagy
 */
@Data
public class WidgetDemoComponentDTO extends ComponentDTO
{
    public static final String OWNERS_NAME_ID = "name";
    public static final String PLACE_OF_BIRTH_ID = "place-of-birth";
    public static final String EMAIL_ID = "email";
    public static final String ROLE_ID = "role";
    public static final String PRICE_ID = "price";
    public static final String COUNT_SAMPLES_ID = "count-samples";
    public static final String ACCOUNT_NO = "account-no";
    public static final String CHECK_IN_DATE_ID = "check-in-date";
    public static final String CHECK_OUT_DATE_ID = "check-out-date";
    public static final String DATE_RANGE_ID = "date-range";
    public static final String SELECT_ID = "select";
    public static final String SELECT2_ID = "select2";
    public static final String SELECT3_ID = "select3";
    public static final String AUTOCOMPLETE_ID = "autocomplete";
    public static final String AUTOCOMPLETE_STREETS_ID = "autocomplete-streets";

    @DTOValue(id = OWNERS_NAME_ID)
    private String ownersName;

    @DTOValue(id = PLACE_OF_BIRTH_ID)
    private String placeOfBirth;

    @DTOValue(id = EMAIL_ID)
    private String email;

    @DTOValue(id = ROLE_ID)
    private String role;

    @DTOValue(id = PRICE_ID)
    private Double price;

    @DTOValue(id = COUNT_SAMPLES_ID)
    private Long countSamples;

    @DTOValue(id = CHECK_IN_DATE_ID)
    private LocalDate checkInDate;

    @DTOValue(id = CHECK_OUT_DATE_ID)
    private LocalDate checkOutDate;

    @DTOValue(id = DATE_RANGE_ID)
    private DateRangeInput.Data dateRange;

    @DTOValue(id = SELECT_ID)
    private Select.Data selectData;

    @DTOValue(id = SELECT2_ID)
    private Select.Data select2Data;

    @DTOValue(id = SELECT3_ID)
    private Select.Data select3Data;

    @DTOValue(id = AUTOCOMPLETE_ID)
    private Autocomplete.Data autocompleteData;

    @DTOValue(id = AUTOCOMPLETE_STREETS_ID)
    private Autocomplete.Data autocompleteStreetsData;
}
