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

package hu.perit.ngface.webservice.service.impl.addressservice;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import hu.perit.ngface.core.types.intf.DataRetrievalParams;
import hu.perit.ngface.data.jpa.service.impl.NgfaceQueryServiceImpl;
import hu.perit.ngface.webservice.config.Constants;
import hu.perit.ngface.webservice.db.addressdb.repo.AddressRepo;
import hu.perit.ngface.webservice.db.addressdb.table.AddressEntity;
import hu.perit.ngface.webservice.model.AddressTableRow;
import hu.perit.ngface.webservice.service.api.AddressService;
import hu.perit.spvitamin.spring.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class AddressServiceImpl extends NgfaceQueryServiceImpl<AddressEntity> implements AddressService
{
    private final AddressRepo repo;


    public AddressServiceImpl(AddressRepo repo)
    {
        super(repo, Constants.DEFAULT_PAGESIZE);
        this.repo = repo;
    }


    @Override
    public void deleteAll()
    {
        this.repo.deleteAll();
    }


    public void loadFromFile(String fileName, String city) throws Exception
    {
        readLineByLine(fileName, city);
    }


    @Override
    public AddressEntity find(Long id) throws ResourceNotFoundException
    {
        try
        {
            return this.repo.getReferenceById(id);
        }
        catch (Exception e)
        {
            throw new ResourceNotFoundException(String.format("No data found with id: '%d'", id));
        }
    }


    protected List<Sort.Order> getDefaultSortOrder()
    {
        return List.of(
            new Sort.Order(Sort.Direction.ASC, AddressTableRow.COL_POSTCODE),
            new Sort.Order(Sort.Direction.ASC, AddressTableRow.COL_STREET)
        );
    }


    @Override
    protected List<?> convertFilterValueSetToDbType(String searchColumn, DataRetrievalParams.Filter filter)
    {
        if (AddressTableRow.COL_POSTCODE.equals(searchColumn))
        {
            return filter.getValueSet().stream()
                .map(DataRetrievalParams.Filter.Item::getText)
                .filter(Objects::nonNull)
                .map(Integer::parseInt)
                .toList();
        }

        return super.convertFilterValueSetToDbType(searchColumn, filter);
    }


    @Override
    public List<String> getDistinctStreets(String searchText)
    {
        if (StringUtils.isBlank(searchText))
        {
            return this.repo.getDistinctStreets();
        }
        return this.repo.getDistinctStreets("%" + searchText + "%");
    }


    @Override
    public List<String> getDistinctDistricts(String searchText)
    {
        if (StringUtils.isBlank(searchText))
        {
            return this.repo.getDistinctDistricts();
        }
        return this.repo.getDistinctDistricts("%" + searchText + "%");
    }


    @Override
    public List<String> getDistinctPostcodes(String searchText)
    {
        if (StringUtils.isNotBlank(searchText))
        {
            return this.repo.getDistinctPostcodes().stream()
                .map(String::valueOf)
                .filter(p -> p.contains(searchText))
                .toList();
        }
        else
        {
            return this.repo.getDistinctPostcodes().stream()
                .map(String::valueOf)
                .toList();
        }
    }


    @Override
    public List<String> getDistinctCities(String searchText)
    {
        if (StringUtils.isBlank(searchText))
        {
            return this.repo.getDistinctCities();
        }
        return this.repo.getDistinctCities("%" + searchText + "%");
    }


    @Override
    @Transactional
    public void update(Long id, Integer postCode, String city, String street, String district)
    {
        this.repo.update(id, postCode, city, street, district);
    }


    private void readLineByLine(String fileName, String city) throws Exception
    {
        try (InputStream resourceAsStream = this.getClass().getResourceAsStream("/" + fileName))
        {
            try (Reader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(resourceAsStream), StandardCharsets.UTF_8)))
            {
                try (CSVReader csvReader = new CSVReaderBuilder(reader)
                    .withCSVParser(new CSVParserBuilder()
                        .withSeparator(';')
                        .build()
                    ).build())
                {
                    String[] line;
                    while ((line = csvReader.readNext()) != null)
                    {
                        processLine(line, city);
                    }
                }
            }
        }
    }


    private void processLine(String[] line, String city)
    {
        if (line.length >= 8)
        {
            AddressEntity entity = new AddressEntity();
            entity.setPostCode(Integer.parseInt(StringUtils.strip(line[0])));
            entity.setCity(city);
            entity.setStreet(StringUtils.strip(line[1]) + " " + StringUtils.strip(line[2]));
            entity.setDistrict(StringUtils.strip(line[3]));
            entity.createSearchField();
            log.debug(entity.toString());
            if (!addressExist(entity))
            {
                this.repo.save(entity);
            }
        }
    }


    private boolean addressExist(AddressEntity entity)
    {
        List<AddressEntity> found = this.repo.findBySearchField(entity.getSearchField());
        return !found.isEmpty();
    }
}
