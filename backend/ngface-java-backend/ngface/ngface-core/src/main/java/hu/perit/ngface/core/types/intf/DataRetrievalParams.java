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

package hu.perit.ngface.core.types.intf;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hu.perit.ngface.core.widget.table.Filterer;
import hu.perit.ngface.core.widget.table.Paginator;
import hu.perit.ngface.core.widget.table.Sorter;
import hu.perit.ngface.core.widget.table.Table;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.BooleanUtils;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Data
public class DataRetrievalParams
{
    @Nullable
    private Page page;
    @Nullable
    private Sort sort;
    @Nullable
    private List<Filter> filters;


    //------------------------------------------------------------------------------------------------------------------
    // Page
    //------------------------------------------------------------------------------------------------------------------
    @Data
    public static class Page
    {
        @NotNull
        private Integer index;

        @NotNull
        private Integer size;


        public Page index(Integer page)
        {
            this.index = page;
            return this;
        }


        public Page size(Integer size)
        {
            this.size = size;
            return this;
        }


        public static Page of(Paginator paginator)
        {
            if (paginator == null)
            {
                return null;
            }

            Page page = new Page();
            page.index = paginator.getPageIndex();
            page.size = paginator.getPageSize();
            return page;
        }
    }


    //------------------------------------------------------------------------------------------------------------------
    // Sort
    //------------------------------------------------------------------------------------------------------------------
    @Data
    public static class Sort
    {
        @NotNull
        private String column;

        @NotNull
        private Direction direction;


        public static Sort of(Sorter sorter)
        {
            if (sorter == null)
            {
                return null;
            }

            Sort sort = new Sort();
            sort.column = sorter.getColumn();
            sort.direction = sorter.getDirection();
            return sort;
        }
    }


    //------------------------------------------------------------------------------------------------------------------
    // Filter
    //------------------------------------------------------------------------------------------------------------------
    @Data
    public static class Filter
    {
        @NotNull
        private String column;

        private ComparisonOperator operator = ComparisonOperator.IN;

        @NotNull
        private List<Item> valueSet;


        public static Filter of(Filterer filterer)
        {
            if (filterer == null)
            {
                return null;
            }

            Filter filter = new Filter();
            filter.column = filterer.getColumn();
            filter.operator = filterer.getOperator();
            filter.valueSet = filterer.getValueSet().getValues().stream()
                    .filter(v -> BooleanUtils.isTrue(v.getSelected()))
                    .map(v -> new Item(v.getText())).toList();
            return filter;
        }


        @Getter
        @ToString
        @RequiredArgsConstructor
        @NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
        public static class Item
        {
            @Nullable
            private final String text;
        }
    }


    //------------------------------------------------------------------------------------------------------------------
    // Cloner
    //------------------------------------------------------------------------------------------------------------------
    @Mapper
    public interface Cloner
    {
        static Cloner getInstance()
        {
            return Mappers.getMapper(Cloner.class);
        }

        @BeanMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
        DataRetrievalParams clone(DataRetrievalParams input);
    }


    public static DataRetrievalParams applyDefaults(DataRetrievalParams dataRetrievalParams, Table.Data defaults)
    {
        return applyDefaults(dataRetrievalParams, defaults, List.of());
    }


    public static DataRetrievalParams applyDefaults(DataRetrievalParams dataRetrievalParams, Table.Data defaults, List<DataRetrievalParams.Filter> activeFilters)
    {
        // Creating a copy or return an empty DataRetrievalParams
        DataRetrievalParams params = Cloner.getInstance().clone(dataRetrievalParams);

        if (defaults == null)
        {
            return params;
        }

        // Page
        Paginator paginator = defaults.getPaginator();
        if (params.getPage() == null && paginator != null)
        {
            // Overwrite from userSettings
            params.setPage(Page.of(paginator));
        }

        // Order
        Sorter sorter = defaults.getSorter();
        if (params.getSort() == null && sorter != null)
        {
            // Overwrite from userSettings
            params.setSort(Sort.of(sorter));
        }

        // Filter
        params.addFilters(activeFilters);
        Map<String, Filterer> defaultFiltererMap = defaults.getFiltererMap();
        if (defaultFiltererMap != null)
        {
            for (Map.Entry<String, Filterer> filtererEntry : defaultFiltererMap.entrySet())
            {
                if (filterNotAvailable(params.getFilters(), filtererEntry.getKey()))
                {
                    Filterer filterer = filtererEntry.getValue();
                    if (filterer != null && BooleanUtils.isTrue(filterer.getActive()))
                    {
                        params.addFilter(Filter.of(filterer));
                    }
                }
            }
        }

        return params;
    }


    private void addFilter(Filter filter)
    {
        if (filter != null)
        {
            if (this.filters == null)
            {
                this.filters = new ArrayList<>();
            }
            this.filters.add(filter);
        }
    }


    private void addFilters(Collection<Filter> filters)
    {
        if (filters != null && !filters.isEmpty())
        {
            if (this.filters == null)
            {
                this.filters = new ArrayList<>();
            }
            this.filters.addAll(filters);
        }
    }


    private static boolean filterNotAvailable(List<Filter> filters, String filtererKey)
    {
        if (filters == null)
        {
            return true;
        }

        return filters.stream().noneMatch(i -> filtererKey.equalsIgnoreCase(i.getColumn()));
    }


    @JsonIgnore
    public int getPageNumber()
    {
        if (page == null)
        {
            return 0;
        }

        return page.index != null ? page.index : 0;
    }


    @JsonIgnore
    public int getPageSize(int defaultPageSize)
    {
        if (page == null)
        {
            return defaultPageSize;
        }

        return page.size != null ? page.size : defaultPageSize;
    }
}
