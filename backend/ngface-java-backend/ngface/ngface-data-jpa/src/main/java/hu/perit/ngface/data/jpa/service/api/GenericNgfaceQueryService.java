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

package hu.perit.ngface.data.jpa.service.api;

import hu.perit.ngface.core.types.intf.DataRetrievalParams;
import hu.perit.ngface.core.types.table.SelectionStore;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GenericNgfaceQueryService<E, ID>
{
    /**
     *
     * @param dataRetrievalParams
     * @return
     */
    Page<E> find(DataRetrievalParams dataRetrievalParams);

    List<String> getDistinctValues(String fieldName, String searchText, Class<E> entityClass, List<DataRetrievalParams.Filter> activeFilters);

    <T> List<String> getDistinctValues(String fieldName, String searchText, Class<E> entityClass, List<DataRetrievalParams.Filter> activeFilters, Class<T> fieldType);

    List<E> findAllByIds(String idFieldName, List<ID> ids);

    Page<E> findAllBySelection(String idFieldName, SelectionStore<?, ID> selectionStore, Pageable pageable);
}
