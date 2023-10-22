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

package hu.perit.ngface.core.types.table;

import hu.perit.ngface.core.widget.table.Table;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class TableSessionDefaults<R extends AbstractTableRow<I>, I> implements Serializable
{
    @Serial
    private static final long serialVersionUID = -4669848429850190591L;

    private Table.Data tableData = new Table.Data();
    private SelectionStore<R, I> selectionStore = new SelectionStore();
}