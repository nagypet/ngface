/*
 * Copyright 2020-2022 the original author or authors.
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

package hu.perit.wsstepbystep.service.impl;

import com.google.common.io.Resources;
import hu.perit.ngface.data.DataRetrievalParams;
import hu.perit.ngface.data.Direction;
import hu.perit.ngface.widget.table.Filterer;
import hu.perit.ngface.widget.table.ValueSet;
import hu.perit.spvitamin.spring.json.JsonSerializable;
import hu.perit.wsstepbystep.config.Constants;
import hu.perit.wsstepbystep.service.api.DemoTableDataProvider;
import hu.perit.wsstepbystep.service.api.Page;
import hu.perit.wsstepbystep.service.api.TableRowDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.math.NumberUtils.min;

@Service
public class DemoTableDataProviderImpl implements DemoTableDataProvider
{
    private Repository repository;

    @PostConstruct
    private void init() throws IOException
    {
        URL url = Resources.getResource("data.json");
        String dataJson = Resources.toString(url, StandardCharsets.UTF_8);
        this.repository = JsonSerializable.fromJson(dataJson, Repository.class);
    }


    @Override
    public Optional<TableRowDTO> getTableRowById(Long id)
    {
        return this.repository.findById(id);
    }


    @Override
    public Page<TableRowDTO> getTableRows(DataRetrievalParams dataRetrievalParams)
    {
        Page<TableRowDTO> retval = new Page<>();

        List<TableRowDTO> filteredResult = this.repository.filterBy(dataRetrievalParams.getFilters());
        retval.setTotalElements(filteredResult.size());

        int pageNumberInt = dataRetrievalParams.getPageNumber();
        int pageSizeInt = dataRetrievalParams.getPageSize(Constants.DEFAULT_PAGESIZE);

        int fromIndex = pageNumberInt * pageSizeInt;
        int toIndex = min(filteredResult.size(), fromIndex + pageSizeInt);

        if (fromIndex >= filteredResult.size())
        {
            return retval;
        }

        Comparator<TableRowDTO> comparator = null;
        if (dataRetrievalParams.getSort() != null)
        {
            String sortColumn = dataRetrievalParams.getSort().getColumn();
            if (StringUtils.isNotBlank(sortColumn))
            {
                if ("id".equals(sortColumn))
                {
                    comparator = Comparator.comparing(TableRowDTO::getId);
                }
                if ("name".equals(sortColumn))
                {
                    comparator = Comparator.comparing(TableRowDTO::getName);
                }
            }
        }

        if (comparator != null && Direction.DESC.equals(dataRetrievalParams.getSort().getDirection()))
        {
            comparator = comparator.reversed();
        }

        if (comparator == null)
        {
            retval.setList(filteredResult.subList(fromIndex, toIndex));
            return retval;
        }

        List<TableRowDTO> sortedList = filteredResult.stream().sorted(comparator).collect(Collectors.toList());
        retval.setList(sortedList.subList(fromIndex, toIndex));
        return retval;
    }


    @Override
    public Filterer getNameFilter()
    {
        ValueSet valueSet = new ValueSet(false);
        valueSet.valueSet(this.repository.getDistinctNames());

        Filterer filterer = new Filterer("name");
        filterer.valueSet(valueSet);
        return filterer;
    }


    @Override
    public Filterer getSymbolFilter()
    {
        ValueSet valueSet = new ValueSet(false);
        valueSet.valueSet(this.repository.getDistinctSymbols());

        Filterer filterer = new Filterer("symbol");
        filterer.valueSet(valueSet);
        return filterer;
    }
}
