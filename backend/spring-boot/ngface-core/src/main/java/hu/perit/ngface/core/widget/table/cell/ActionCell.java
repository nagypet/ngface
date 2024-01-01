/*
 * Copyright 2020-2024 the original author or authors.
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

package hu.perit.ngface.core.widget.table.cell;

import hu.perit.ngface.core.widget.table.Action;

import java.util.ArrayList;
import java.util.List;

public class ActionCell extends Cell<List<Action>, ActionCell>
{
    public ActionCell(List<Action> value)
    {
        super(new ArrayList<>(value));
    }

    public ActionCell add(Action action)
    {
        this.value.add(action);
        return this;
    }
}
