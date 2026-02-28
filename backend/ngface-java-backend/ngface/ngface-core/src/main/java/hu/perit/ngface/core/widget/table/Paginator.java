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

import com.fasterxml.jackson.annotation.JsonIgnore;
import hu.perit.spvitamin.core.typehelpers.IntUtils;
import hu.perit.spvitamin.core.typehelpers.LongUtils;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@RequiredArgsConstructor
@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@EqualsAndHashCode
public class Paginator implements Serializable
{
    @Serial
    private static final long serialVersionUID = -6946217215070494410L;

    private final Integer pageIndex;
    private final Integer pageSize;
    private final Long length;
    private final List<Integer> pageSizeOptions;


    public static Paginator of(Integer pageIndex, Integer pageSize, Long length, List<Integer> pageSizeOptions)
    {
        return new Paginator(pageIndex, pageSize, length, pageSizeOptions);
    }


    public static Paginator validPaginator(Paginator input)
    {
        if (input == null)
        {
            return null;
        }

        // Constraints:
        // - pageSize >= 1
        // - pageIndex >= 0
        // - pageIndex <= lastPageIndex
        // - length >= 0
        int pageSize = IntUtils.max(1, input.getPageSize());
        int pageIndex = Math.max(0, IntUtils.min(input.getLastPageIndex(), input.getPageIndex()));
        long pageLength = LongUtils.max(0L, input.getLength());
        return new Paginator(pageIndex, pageSize, pageLength, input.getPageSizeOptions());
    }


    @JsonIgnore
    public int getLastPageIndex()
    {
        if (IntUtils.get(pageSize) == 0 || LongUtils.get(length) == 0)
        {
            return 0;
        }
        return (int) Math.ceil(LongUtils.get(length) / (double) IntUtils.get(pageSize)) - 1;
    }
}
