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

package hu.perit.ngface.webservice.db.addressdb.repo;

import hu.perit.ngface.data.jpa.service.impl.NgfaceQueryRepo;
import hu.perit.ngface.webservice.db.addressdb.table.AddressEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AddressRepo extends NgfaceQueryRepo<AddressEntity>
{
    List<AddressEntity> findBySearchField(String searchField);

    @Query("select distinct e.street from AddressEntity e")
    List<String> getDistinctStreets();

    @Query("select distinct e.street from AddressEntity e where e.street ilike :searchText")
    List<String> getDistinctStreets(@Param(value = "searchText") String searchText);

    @Query("select distinct e.district from AddressEntity e")
    List<String> getDistinctDistricts();

    @Query("select distinct e.district from AddressEntity e where e.district ilike :searchText")
    List<String> getDistinctDistricts(@Param(value = "searchText") String searchText);

    @Query("select distinct e.postCode from AddressEntity e")
    List<Integer> getDistinctPostcodes();

    @Query("select distinct e.city from AddressEntity e")
    List<String> getDistinctCities();

    @Query("select distinct e.city from AddressEntity e where e.city ilike :searchText")
    List<String> getDistinctCities(@Param(value = "searchText") String searchText);

    @Modifying
    @Query("update AddressEntity e set e.postCode = :postCode, e.city = :city, e.street = :street, e.district = :district where e.id = :id")
    void update(
        @Param(value = "id") Long id,
        @Param(value = "postCode") Integer postCode,
        @Param(value = "city") String city,
        @Param(value = "street") String street,
        @Param(value = "district") String district);
}
