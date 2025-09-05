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

import hu.perit.spvitamin.spring.json.JSonSerializer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class ActionTest
{
    @Test
    void testSimpleAction() throws IOException
    {
        // Create a simple action
        Action input = new Action("test-action")
                .label("Test Action")
                .icon("test-icon")
                .enabled(true)
                .badge("1")
                .style(Action.Style.BUTTON);

        // Serialize to JSON
        String inputAsString = JSonSerializer.toJson(input);
        log.debug(inputAsString);
        
        // Deserialize from JSON
        Action result = JSonSerializer.fromJson(inputAsString, Action.class);
        
        // Verify the result
        assertThat(result).isEqualTo(input);
    }

    @Test
    void testActionGroup() throws IOException
    {
        // Create child actions
        Action childAction1 = new Action("child-action-1")
                .label("Child Action 1")
                .icon("child-icon-1")
                .style(Action.Style.ICON);

        Action childAction2 = new Action("child-action-2")
                .label("Child Action 2")
                .icon("child-icon-2")
                .style(Action.Style.BUTTON);

        // Create parent action with ACTION_GROUP style
        Action input = new Action("parent-action")
                .label("Parent Action")
                .icon("parent-icon")
                .style(Action.Style.ACTION_GROUP)
                .actions(Arrays.asList(childAction1, childAction2));

        // Serialize to JSON
        String inputAsString = JSonSerializer.toJson(input);
        log.debug(inputAsString);
        
        // Deserialize from JSON
        Action result = JSonSerializer.fromJson(inputAsString, Action.class);
        
        // Verify the result
        assertThat(result).isEqualTo(input);
        
        // Verify child actions
        assertThat(result.getActions()).hasSize(2);
        assertThat(result.getActions().get(0)).isEqualTo(childAction1);
        assertThat(result.getActions().get(1)).isEqualTo(childAction2);
    }

    @Test
    void testNestedActionGroups() throws IOException
    {
        // Create nested structure of action groups
        Action deepChildAction = new Action("deep-child")
                .label("Deep Child")
                .icon("deep-icon")
                .style(Action.Style.ICON);

        Action middleAction = new Action("middle-action")
                .label("Middle Action")
                .icon("middle-icon")
                .style(Action.Style.ACTION_GROUP)
                .addAction(deepChildAction);

        Action input = new Action("root-action")
                .label("Root Action")
                .icon("root-icon")
                .style(Action.Style.ACTION_GROUP)
                .addAction(middleAction);

        // Serialize to JSON
        String inputAsString = JSonSerializer.toJson(input);
        log.debug(inputAsString);
        
        // Deserialize from JSON
        Action result = JSonSerializer.fromJson(inputAsString, Action.class);
        
        // Verify the result
        assertThat(result).isEqualTo(input);
        
        // Verify nested structure
        assertThat(result.getActions()).hasSize(1);
        Action resultMiddle = result.getActions().get(0);
        assertThat(resultMiddle.getActions()).hasSize(1);
        Action resultDeep = resultMiddle.getActions().get(0);
        assertThat(resultDeep.getStyle()).isEqualTo(Action.Style.ICON);
    }
}
