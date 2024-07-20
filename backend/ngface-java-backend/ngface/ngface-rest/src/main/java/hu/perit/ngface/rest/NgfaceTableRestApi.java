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

package hu.perit.ngface.rest;

import hu.perit.ngface.core.types.intf.DataRetrievalParams;
import hu.perit.ngface.core.types.intf.RowSelectParams;
import hu.perit.ngface.core.types.intf.SubmitFormData;
import hu.perit.ngface.core.types.intf.TableActionParams;
import hu.perit.ngface.core.widget.form.Form;
import hu.perit.ngface.core.widget.table.Filterer;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

public interface NgfaceTableRestApi<I>
{
    String URL_GET = "/get-table";
    String URL_COLVALUESET = "/filterer";
    String URL_SELECT = "/row-select";
    String URL_ACTIONCLICK = "/action-click";

    @PostMapping(URL_GET)
    Form getTable(@Valid @RequestBody DataRetrievalParams dataRetrievalParams);

    @GetMapping
    Form getTableRow(@RequestParam I rowId);

    @GetMapping(URL_COLVALUESET)
    Filterer getColumnFilterer(
        @RequestParam(value = "column") String column,
        @RequestParam(value = "searchText") String searchText
    );


    @PutMapping(URL_SELECT)
    void onRowSelect(@Valid @RequestBody RowSelectParams<I> rowSelectParams) throws Exception;

    @PutMapping(URL_ACTIONCLICK)
    void onActionClick(@Valid @RequestBody TableActionParams<I> tableActionParams) throws Exception;

    @PostMapping
    void submitTable(@RequestBody SubmitFormData submitFormData);
}
