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

package hu.perit.ngface.core.widget.table;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FiltererTest
{
    @Test
    void testEqual()
    {
        Filterer filterer1 = new Filterer("col").active(true).valueSet(new ValueSet(true).values(List.of("alma", "korte")));
        Filterer filterer2 = new Filterer("col").active(true).valueSet(new ValueSet(true).values(List.of("alma", "korte")));
        assertThat(filterer1).isEqualTo(filterer2);
    }
}
