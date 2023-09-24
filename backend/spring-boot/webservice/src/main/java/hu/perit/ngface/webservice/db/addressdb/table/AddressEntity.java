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

package hu.perit.ngface.webservice.db.addressdb.table;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
@ToString
@Entity
@Table(name = AddressEntity.TABLE_NAME, indexes = {
    @Index(name = "idx_" + AddressEntity.FIELD_POSTCODE, columnList = AddressEntity.FIELD_POSTCODE),
    @Index(name = "idx_" + AddressEntity.FIELD_CITY, columnList = AddressEntity.FIELD_CITY),
    @Index(name = "idx_" + AddressEntity.FIELD_STREET, columnList = AddressEntity.FIELD_STREET),
    @Index(name = "idx_" + AddressEntity.FIELD_DISTRICT, columnList = AddressEntity.FIELD_DISTRICT),
    @Index(name = "idx_" + AddressEntity.FIELD_SEARCH, columnList = AddressEntity.FIELD_SEARCH, unique = true)
})
public class AddressEntity
{
    public static final String TABLE_NAME = "address";
    public static final String FIELD_POSTCODE = "postcode";
    public static final String FIELD_CITY = "city";
    public static final String FIELD_STREET = "street";
    public static final String FIELD_DISTRICT = "district";
    public static final String FIELD_SEARCH = "search";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "bigserial")
    private Long id;

    @Column(name = FIELD_POSTCODE)
    private Integer postCode;

    @Column(name = FIELD_CITY)
    private String city;

    @Column(name = FIELD_STREET)
    private String street;

    @Column(name = FIELD_DISTRICT)
    private String district;

    @Column(name = FIELD_SEARCH)
    private String searchField;


    public void createSearchField()
    {
        this.searchField = textField(String.valueOf(this.postCode)) + textField(this.city) + textField(this.street) + textField(this.district);
    }


    private String textField(String text)
    {
        if (StringUtils.isBlank(text))
        {
            return "";
        }
        return text.replaceAll("\\s", "").toLowerCase();
    }
}
