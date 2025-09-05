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

package hu.perit.ngface.core.types.table;

import hu.perit.ngface.core.widget.table.Filterer;
import hu.perit.ngface.core.widget.table.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TableSessionDefaults<R extends AbstractTableRow<I>, I extends Serializable> implements Serializable
{
    @Serial
    private static final long serialVersionUID = 7040106510650370072L;

    private Table.Data tableData = new Table.Data();
    private SelectionStore<R, I> selectionStore;
    @Setter(AccessLevel.NONE)
    private List<Filterer> defaultFilterers = new ArrayList<>();


    public TableSessionDefaults(Table.SelectMode selectMode)
    {
        this.selectionStore = new SelectionStore<>(selectMode);
    }
}
