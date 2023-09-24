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

package hu.perit.ngface.webservice.model;

import lombok.Data;

@Data
public class AddressDTO
{
   public static final String COL_ID = "id";
   public static final String COL_POSTCODE = "postCode";
   public static final String COL_CITY = "city";
   public static final String COL_STREET = "street";
   public static final String COL_DISTRICT = "district";

   private String id;
   private String postCode;
   private String city;
   private String street;
   private String district;
}
