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
import hu.perit.spvitamin.core.typehelpers.ListUtils;
import hu.perit.spvitamin.core.util.FieldMapper;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public abstract class GenericNgfaceQueryServiceImpl<E, ID extends Serializable> implements GenericNgfaceQueryService<E, ID>
{
    private final GenericNgfaceQueryRepo<E, ID> repo;
    private final int defaultPageSize;


    @Override
    public Page<E> find(DataRetrievalParams dataRetrievalParams)
    {
        log.debug("find({})", dataRetrievalParams);

        int pageNumberInt = dataRetrievalParams.getPageNumber();
        int pageSizeInt = dataRetrievalParams.getPageSize(defaultPageSize);

        PageRequest pageRequest = PageRequest.of(pageNumberInt, pageSizeInt, Sort.by(withSwimlanes(getSortOrder(dataRetrievalParams.getSort()))));

        Specification<E> spec = getSpecificationByFilters(dataRetrievalParams.getFilters());
        if (spec != null)
        {
            return this.repo.findAll(spec, pageRequest);
        }

        return this.repo.findAll(pageRequest);
    }


    protected List<Sort.Order> getSortOrder(DataRetrievalParams.Sort sort)
    {
        if (sort != null
                && StringUtils.isNotBlank(sort.getColumn())
                && !Direction.UNDEFINED.equals(sort.getDirection()))
        {
            return List.of(new Sort.Order(getDirection(sort.getDirection()), sort.getColumn()));
        }

        return getDefaultSortOrder();
    }


    protected static Sort.Direction getDirection(Direction direction)
    {
        return Direction.ASC.equals(direction) ?
                Sort.Direction.ASC :
                Sort.Direction.DESC;
    }


    List<Sort.Order> withSwimlanes(List<Sort.Order> sortOrder)
    {
        List<Sort.Order> result = new ArrayList<>();
        result.addAll(getSwimlanes());
        result.addAll(sortOrder);
        return result;
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
                    spec = spec == null ? byFilter(filter) : spec.and(byFilter(filter));
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

            switch (filter.getOperator())
            {
                case EQ:
                    if (valueSet.isEmpty())
                    {
                        return criteriaBuilder.conjunction();
                    }
                    Object value = ListUtils.first(valueSet);
                    if (value == null)
                    {
                        return criteriaBuilder.isNull(root.get(searchColumn));
                    }
                    return criteriaBuilder.equal(root.get(searchColumn), value);

                case NEQ:
                    if (valueSet.isEmpty())
                    {
                        return criteriaBuilder.conjunction();
                    }
                    Object neqValue = ListUtils.first(valueSet);
                    if (neqValue == null)
                    {
                        return criteriaBuilder.isNotNull(root.get(searchColumn));
                    }
                    return criteriaBuilder.notEqual(root.get(searchColumn), neqValue);

                case GT:
                    if (ListUtils.first(valueSet) == null)
                    {
                        return criteriaBuilder.conjunction();
                    }
                    return criteriaBuilder.greaterThan(root.get(searchColumn), (Comparable) ListUtils.first(valueSet));

                case GTE:
                    if (ListUtils.first(valueSet) == null)
                    {
                        return criteriaBuilder.conjunction();
                    }
                    return criteriaBuilder.greaterThanOrEqualTo(root.get(searchColumn), (Comparable) ListUtils.first(valueSet));

                case LT:
                    if (ListUtils.first(valueSet) == null)
                    {
                        return criteriaBuilder.conjunction();
                    }
                    return criteriaBuilder.lessThan(root.get(searchColumn), (Comparable) ListUtils.first(valueSet));

                case LTE:
                    if (ListUtils.first(valueSet) == null)
                    {
                        return criteriaBuilder.conjunction();
                    }
                    return criteriaBuilder.lessThanOrEqualTo(root.get(searchColumn), (Comparable) ListUtils.first(valueSet));

                case BETWEEN:
                    // Support partial ranges: if only start or only end is provided, use >= or <= respectively
                    Object start = valueSet.size() >= 1 ? valueSet.get(0) : null;
                    Object end = valueSet.size() >= 2 ? valueSet.get(1) : null;
                    if (start == null && end == null)
                    {
                        return criteriaBuilder.conjunction();
                    }
                    if (start != null && end != null)
                    {
                        return criteriaBuilder.between(root.get(searchColumn),
                                (Comparable) start,
                                (Comparable) end);
                    }
                    if (start != null)
                    {
                        return criteriaBuilder.greaterThanOrEqualTo(root.get(searchColumn), (Comparable) start);
                    }
                    // end != null
                    return criteriaBuilder.lessThanOrEqualTo(root.get(searchColumn), (Comparable) end);

                case LIKE:
                    if (ListUtils.first(valueSet) == null)
                    {
                        return criteriaBuilder.conjunction();
                    }
                    String likeValue = ListUtils.first(valueSet).toString();
                    return criteriaBuilder.like(criteriaBuilder.lower(root.get(searchColumn)),
                            "%" + likeValue.toLowerCase() + "%");

                case IN:
                default:
                    // Handle IN operator (default case)
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
                    List<?> nonNullValues = new ArrayList<>(valueSet);
                    nonNullValues.remove(null);
                    return criteriaBuilder.or(
                            criteriaBuilder.isNull(root.get(searchColumn)),
                            criteriaBuilder.in(root.get(searchColumn)).value(nonNullValues)
                    );
            }
        };
    }


    /**
     * Method to retrieve a list of swim lane sorting orders. This sort orders gets priority over all other
     * sort orders and will be kept even if the user chooses his own sort order.
     *
     * @return a list of {@link Sort.Order} objects representing the sorting order of swim lanes
     */
    protected List<Sort.Order> getSwimlanes()
    {
        return List.of();
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
        if (StringUtils.isNotBlank(searchText))
        {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get(fieldName)), "%" + searchText.toLowerCase() + "%"));
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
    public <T> List<String> getMinMaxValues(String fieldName, String searchText, Class<E> entityClass, List<DataRetrievalParams.Filter> activeFilters, Class<T> fieldType)
    {
        CriteriaBuilder criteriaBuilder = this.getEntityManager().getCriteriaBuilder();

        // Additional selection criteria
        List<DataRetrievalParams.Filter> appliedFilters = new ArrayList<>(getDefaultFilters());
        if (activeFilters != null)
        {
            appliedFilters.addAll(activeFilters);
        }

        // For numeric types, we can use MIN and MAX aggregate functions
        if (Number.class.isAssignableFrom(fieldType) ||
                fieldType == Integer.class ||
                fieldType == Long.class ||
                fieldType == Double.class ||
                fieldType == Float.class)
        {
            // Create a query that returns a tuple with MIN and MAX values
            CriteriaQuery<Object[]> query = criteriaBuilder.createQuery(Object[].class);
            Root<E> root = query.from(entityClass);

            query.multiselect(
                    criteriaBuilder.min(root.get(fieldName)),
                    criteriaBuilder.max(root.get(fieldName))
            );

            // Apply filters
            if (!appliedFilters.isEmpty())
            {
                Specification<E> specificationByFilters = getSpecificationByFilters(appliedFilters);
                Predicate additionalPredicate = specificationByFilters.toPredicate(root, query, criteriaBuilder);
                if (additionalPredicate != null)
                {
                    query.where(additionalPredicate);
                }
            }

            // Execute query
            Object[] result = getEntityManager().createQuery(query).getSingleResult();

            // Process results
            List<String> stringResults = new ArrayList<>();
            if (result.length > 0)
            {
                stringResults.add(FieldMapper.toBigDecimal(result[0]).toString());
            }

            if (result.length > 1)
            {
                stringResults.add(FieldMapper.toBigDecimal(result[1]).toString());
            }

            return stringResults;
        }
        else
        {
            throw new IllegalArgumentException("The field type is not a numeric type: " + fieldType.getName());
        }
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

        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(withSwimlanes(getDefaultSortOrder())));
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
