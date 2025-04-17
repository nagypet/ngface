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
import hu.perit.ngface.data.jpa.service.api.NgfaceQueryService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public abstract class GenericNgfaceQueryServiceImpl<E, ID> implements NgfaceQueryService<E>
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
     *  Example:
     *  protected List<Sort.Order> getDefaultSortOrder()
     *  {
     *      return List.of(
     *              new Sort.Order(Sort.Direction.ASC, AddressDTO.COL_POSTCODE),
     *              new Sort.Order(Sort.Direction.ASC, AddressDTO.COL_STREET)
     *      );
     *  }
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
}
