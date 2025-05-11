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

package hu.perit.ngface.data.jpa.service.impl;

import hu.perit.ngface.core.types.intf.DataRetrievalParams;
import hu.perit.ngface.core.types.intf.Direction;
import hu.perit.ngface.core.types.intf.RowSelectParams;
import hu.perit.ngface.core.types.table.SelectionStore;
import hu.perit.ngface.data.jpa.service.api.GenericNgfaceQueryService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public abstract class GenericNgfaceQueryServiceImpl<E, ID> implements GenericNgfaceQueryService<E, ID>
{
    private final GenericNgfaceQueryRepo<E, ID> repo;
    private final int defaultPageSize;


    @Override
    public Page<E> find(DataRetrievalParams dataRetrievalParams)
    {
        log.debug("find({})", dataRetrievalParams);

        int pageNumberInt = dataRetrievalParams.getPageNumber();
        int pageSizeInt = dataRetrievalParams.getPageSize(defaultPageSize);

        PageRequest pageRequest;
        if (dataRetrievalParams.getSort() != null
                && StringUtils.isNotBlank(dataRetrievalParams.getSort().getColumn())
                && !Direction.UNDEFINED.equals(dataRetrievalParams.getSort().getDirection()))
        {
            Sort.Order sortOrder = new Sort.Order(
                    Direction.ASC.equals(dataRetrievalParams.getSort().getDirection()) ?
                            Sort.Direction.ASC :
                            Sort.Direction.DESC,
                    dataRetrievalParams.getSort().getColumn());

            pageRequest = PageRequest.of(pageNumberInt, pageSizeInt, Sort.by(sortOrder));
        }
        else
        {
            pageRequest = PageRequest.of(pageNumberInt, pageSizeInt, Sort.by(getDefaultSortOrder()));
        }

        Specification<E> spec = getSpecificationByFilters(dataRetrievalParams.getFilters());
        if (spec != null)
        {
            return this.repo.findAll(spec, pageRequest);
        }

        return this.repo.findAll(pageRequest);
    }


    protected Specification<E> getSpecificationByFilters(List<DataRetrievalParams.Filter> filters)
    {
        // Default filters allow a 'hard-coded' filter to be applied always for the given view
        List<DataRetrievalParams.Filter> appliedFilters = new ArrayList<>(getDefaultFilters());
        if (filters != null)
        {
            appliedFilters.addAll(filters);
        }

        if (!appliedFilters.isEmpty())
        {
            Specification<E> spec = null;
            for (DataRetrievalParams.Filter filter : appliedFilters)
            {
                if (StringUtils.isNotBlank(filter.getColumn()))
                {
                    if (spec == null)
                    {
                        spec = byFilter(filter);
                    }
                    else
                    {
                        spec = spec.and(byFilter(filter));
                    }
                }
            }
            return spec;
        }

        return null;
    }


    protected List<DataRetrievalParams.Filter> getDefaultFilters()
    {
        return List.of();
    }


    protected Specification<E> byFilter(DataRetrievalParams.Filter filter)
    {
        if (filter == null)
        {
            return null;
        }

        String searchColumn = getSearchColumn(filter.getColumn());
        return (root, query, criteriaBuilder) -> {
            List<?> valueSet = convertFilterValueSetToDbType(searchColumn, filter);

            if (!valueSet.contains(null))
            {
                // There is no (Blanks)
                return criteriaBuilder.in(root.get(searchColumn)).value(valueSet);
            }
            else if (valueSet.size() == 1)
            {
                // Only (Blanks) is selected
                return criteriaBuilder.isNull(root.get(searchColumn));
            }

            // (Blanks) with some others selected
            valueSet.remove(null);
            return criteriaBuilder.or(
                    criteriaBuilder.isNull(root.get(searchColumn)),
                    criteriaBuilder.in(root.get(searchColumn)).value(valueSet)
            );
        };
    }


    /**
     * Example:
     * protected List<Sort.Order> getDefaultSortOrder()
     * {
     * return List.of(
     * new Sort.Order(Sort.Direction.ASC, AddressDTO.COL_POSTCODE),
     * new Sort.Order(Sort.Direction.ASC, AddressDTO.COL_STREET)
     * );
     * }
     *
     * @return
     */
    protected abstract List<Sort.Order> getDefaultSortOrder();


    /**
     * Sometimes the column name where we want to search does not match with the column name in the filter.
     *
     * @param column
     * @return
     */
    protected String getSearchColumn(String column)
    {
        return column;
    }


    /**
     * This is where the conversion of the values in the filter takes place. The filter contains always strings.
     * The type of resulting list must be compatible with the type of the searchColumn.
     *
     * @param searchColumn
     * @param filter
     * @return
     */
    protected List<?> convertFilterValueSetToDbType(String searchColumn, DataRetrievalParams.Filter filter)
    {
        return filter.getValueSet().stream()
                .map(DataRetrievalParams.Filter.Item::getText)
                .toList();
    }


    protected abstract EntityManager getEntityManager();


    @Override
    public List<String> getDistinctValues(String fieldName, String searchText, Class<E> entityClass, List<DataRetrievalParams.Filter> activeFilters)
    {
        CriteriaBuilder criteriaBuilder = this.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<String> query = criteriaBuilder.createQuery(String.class);
        Root<E> root = query.from(entityClass);

        query.distinct(true);
        query.select(root.get(fieldName));

        // Default WHERE condition
        Predicate predicate = criteriaBuilder.conjunction();

        // If there is a searchText
        if (searchText != null)
        {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get(fieldName)), "%" + searchText + "%"));
        }

        // Additional selection criteria
        List<DataRetrievalParams.Filter> appliedFilters = new ArrayList<>(getDefaultFilters());
        if (activeFilters != null)
        {
            appliedFilters.addAll(activeFilters);
        }
        if (!appliedFilters.isEmpty())
        {
            Specification<E> specificationByFilters = getSpecificationByFilters(appliedFilters);
            Predicate additionalPredicate = specificationByFilters.toPredicate(root, query, criteriaBuilder);
            if (additionalPredicate != null)
            {
                predicate = criteriaBuilder.and(predicate, additionalPredicate);
            }
        }

        query.where(predicate);

        return getEntityManager().createQuery(query).getResultList();
    }


    @Override
    public <T> List<String> getDistinctValues(String fieldName, String searchText, Class<E> entityClass, List<DataRetrievalParams.Filter> activeFilters, Class<T> fieldType)
    {
        CriteriaBuilder criteriaBuilder = this.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> query = criteriaBuilder.createQuery(fieldType);
        Root<E> root = query.from(entityClass);

        query.distinct(true);
        query.select(root.get(fieldName));

        // Default WHERE condition
        Predicate predicate = criteriaBuilder.conjunction();

        // Additional selection criteria
        List<DataRetrievalParams.Filter> appliedFilters = new ArrayList<>(getDefaultFilters());
        if (activeFilters != null)
        {
            appliedFilters.addAll(activeFilters);
        }
        if (!appliedFilters.isEmpty())
        {
            Specification<E> specificationByFilters = getSpecificationByFilters(appliedFilters);
            Predicate additionalPredicate = specificationByFilters.toPredicate(root, query, criteriaBuilder);
            if (additionalPredicate != null)
            {
                predicate = criteriaBuilder.and(predicate, additionalPredicate);
            }
        }

        query.where(predicate);

        List<T> resultList = getEntityManager().createQuery(query).getResultList();

        // If there is no searchText
        if (searchText == null)
        {
            return resultList.stream().map(String::valueOf).toList();
        }

        return resultList.stream().map(String::valueOf).filter(i -> i.contains(searchText)).toList();
    }


    @Override
    public List<E> findAllByIds(String idFieldName, List<ID> ids)
    {
        DataRetrievalParams.Filter filter = new DataRetrievalParams.Filter();
        filter.setColumn(idFieldName);
        filter.setValueSet(ids.stream().map(i -> new DataRetrievalParams.Filter.Item(String.valueOf(i))).toList());

        List<DataRetrievalParams.Filter> appliedFilters = new ArrayList<>(getDefaultFilters());
        appliedFilters.add(filter);

        Specification<E> spec = getSpecificationByFilters(appliedFilters);
        return this.repo.findAll(spec);
    }


    @Override
    public Page<E> findAllBySelection(String idFieldName, SelectionStore<?, ID> selectionStore, Pageable pageable)
    {
        if (selectionStore.getSelectMode() == RowSelectParams.SelectMode.SINGLE || selectionStore.getSelectMode() == RowSelectParams.SelectMode.ALL_UNCHECKED)
        {
            return new PageImpl<>(findAllByIds(idFieldName, selectionStore.getSelectedRowIds()));
        }

        // We have to select all available items but the ones stored in the selectionStore
        List<DataRetrievalParams.Filter> appliedFilters = new ArrayList<>(getDefaultFilters());
        Specification<E> spec = getSpecificationByFilters(appliedFilters);
        if (!selectionStore.getUnselectedRowIds().isEmpty())
        {
            spec = spec.and(notIn(idFieldName, selectionStore.getUnselectedRowIds()));
        }

        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(getDefaultSortOrder()));
        return this.repo.findAll(spec, pageRequest);
    }


    private Specification<E> notIn(String idFieldName, List<ID> valueSet)
    {
        return (root, query, criteriaBuilder) -> {
            // (Blanks) with some others selected
            return criteriaBuilder.in(root.get(idFieldName)).value(valueSet).not();
        };
    }
}
