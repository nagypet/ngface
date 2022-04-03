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

package hu.perit.wsstepbystep.ngface.democomponent;

import hu.perit.ngface.data.ComponentData;
import hu.perit.ngface.data.WData;
import hu.perit.ngface.widget.input.DateRangeInput;
import hu.perit.ngface.widget.input.Select;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;

/**
 * This is the actual DTO object which have to be shown. This data object will be enriched with view information so that
 * the frontend receives all necessary information to render the widget.
 *
 * @author Peter Nagy
 */
@Data
@Slf4j
public class DemoComponentData extends ComponentData
{
    public static final String OWNERS_NAME_ID = "name";
    public static final String PLACE_OF_BIRTH_ID = "place-of-birth";
    public static final String EMAIL_ID = "email";
    public static final String ROLE_ID = "role";
    public static final String AMOUNT_ID = "amount";
    public static final String COUNT_SAMPLES_ID = "count-samples";
    public static final String ACCOUNT_NO = "account-no";
    public static final String CHECK_IN_DATE_ID = "check-in-date";
    public static final String CHECK_OUT_DATE_ID = "check-out-date";
    public static final String DATE_RANGE_ID = "date-range";
    public static final String SELECT_ID = "select";
    public static final String SELECT2_ID = "select2";
    public static final String SELECT3_ID = "select3";

    @WData(id = OWNERS_NAME_ID)
    private String ownersName;

    @WData(id = PLACE_OF_BIRTH_ID)
    private String placeOfBirth;

    @WData(id = EMAIL_ID)
    private String email;

    @WData(id = ROLE_ID)
    private String role;

    @WData(id = AMOUNT_ID)
    private Double amount;

    @WData(id = COUNT_SAMPLES_ID)
    private Long countSamples;

    @WData(id = CHECK_IN_DATE_ID)
    private LocalDate checkInDate;

    @WData(id = CHECK_OUT_DATE_ID)
    private LocalDate checkOutDate;

    @WData(id = DATE_RANGE_ID)
    private DateRangeInput.Data dateRange;

    @WData(id = SELECT_ID)
    private Select.Data selectData;

    @WData(id = SELECT2_ID)
    private Select.Data select2Data;

    @WData(id = SELECT3_ID)
    private Select.Data select3Data;

    private List<DemoTableDataProvider.DataRow> tableRows;
    private int totalTableRowCount;
}
