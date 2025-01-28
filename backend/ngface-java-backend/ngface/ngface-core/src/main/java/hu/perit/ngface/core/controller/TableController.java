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

package hu.perit.ngface.core.controller;

import hu.perit.ngface.core.types.intf.DataRetrievalParams;
import hu.perit.ngface.core.types.intf.RowSelectParams;
import hu.perit.ngface.core.types.intf.TableActionParams;
import hu.perit.ngface.core.types.table.AbstractTableRow;
import hu.perit.ngface.core.types.table.TableSessionDefaults;
import hu.perit.ngface.core.widget.table.Filterer;
import hu.perit.ngface.core.widget.table.FiltererFactory;

public interface TableController<D, R extends AbstractTableRow<I>, I>
{
    D getTable(DataRetrievalParams dataRetrievalParams);

    D getTableRow(I rowId);

    Filterer getFilterer(String column, String searchText);

    FiltererFactory getFiltererFactory();

    void onSave(D data);

    /**
     *
     * @param tableActionParams
     */
    void onActionClick(TableActionParams<I> tableActionParams) throws Exception;


    /**
     *
     * @param rowSelectParams
     * @throws Exception
     */
    void onRowSelect(RowSelectParams<I> rowSelectParams) throws Exception;

    TableSessionDefaults<R, I> getSessionDefaults();

    void saveSessionDefaults(TableSessionDefaults<R, I> defaults);
}
