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

import hu.perit.ngface.core.types.intf.ComparisonOperator;
import lombok.*;
import org.apache.commons.lang3.BooleanUtils;

import java.io.Serial;
import java.io.Serializable;

@ToString
@Getter
@RequiredArgsConstructor()
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@EqualsAndHashCode
public class Filterer implements Serializable
{
    @Serial
    private static final long serialVersionUID = 1181873727453833393L;


    public enum Type
    {
        TEXT,
        NUMBER,
        DATE,
        DATETIME,
        BOOLEAN
    }

    private final String column;
    private ComparisonOperator operator = ComparisonOperator.IN;
    private ValueSet valueSet = new ValueSet(false);
    private String searchText = "";
    private Boolean active = Boolean.FALSE;
    private Type type = Type.TEXT;
    private Integer order;


    public Filterer operator(ComparisonOperator operator)
    {
        this.operator = operator;
        return this;
    }


    public Filterer valueSet(ValueSet valueSet)
    {
        this.valueSet = valueSet;
        return this;
    }


    public Filterer searchText(String searchText)
    {
        this.searchText = searchText;
        return this;
    }


    public Filterer active(Boolean active)
    {
        this.active = BooleanUtils.isTrue(active);
        return this;
    }


    public Filterer type(Type type)
    {
        this.type = type;
        return this;
    }


    public Filterer order(Integer order)
    {
        this.order = order;
        return this;
    }


    public void clear()
    {
        this.searchText = "";
        this.valueSet.getValues().forEach(i -> i.selected(true));
        this.active = Boolean.FALSE;
    }
}
