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

package hu.perit.ngface.webservice.config;

import hu.perit.ngface.core.formating.NumericFormat;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constants
{
    public static final int DEFAULT_PAGESIZE = 10;

    public static final NumericFormat ATOMIC_WEIGHT_FORMAT = new NumericFormat().precision(4).suffix("g/mol");

    public static final int ADDRESS_API_SEARCH = 1;

    public static final int DEMO_FORM_CONTROLLER_BASE = 100;
    public static final int DEMO_FORM_CONTROLLER_GET_FORM = DEMO_FORM_CONTROLLER_BASE + 1;
    public static final int DEMO_FORM_CONTROLLER_SUBMIT_FORM = DEMO_FORM_CONTROLLER_BASE + 2;

    public static final int DEMO_FORM_TABLE_CONTROLLER_BASE = 200;
    public static final int DEMO_FORM_TABLE_CONTROLLER_GET_TABLE = DEMO_FORM_TABLE_CONTROLLER_BASE + 1;
    public static final int DEMO_FORM_TABLE_CONTROLLER_GET_TABLE_ROW = DEMO_FORM_TABLE_CONTROLLER_BASE + 2;
    public static final int DEMO_FORM_TABLE_CONTROLLER_GET_COLUMN_FILTERER = DEMO_FORM_TABLE_CONTROLLER_BASE + 3;
    public static final int DEMO_FORM_TABLE_CONTROLLER_ON_ROW_SELECT = DEMO_FORM_TABLE_CONTROLLER_BASE + 4;
    public static final int DEMO_FORM_TABLE_CONTROLLER_SUBMIT_TABLE = DEMO_FORM_TABLE_CONTROLLER_BASE + 5;

    public static final int TABLE_DETAILS_CONTROLLER_BASE = 300;
    public static final int TABLE_DETAILS_CONTROLLER_GET_FORM = TABLE_DETAILS_CONTROLLER_BASE + 1;
    public static final int TABLE_DETAILS_CONTROLLER_SUBMIT_FORM = TABLE_DETAILS_CONTROLLER_BASE + 2;

    public static final String SUBSYSTEM_NAME = "ngface-demo-app";
}
