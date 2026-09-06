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

package hu.perit.ngface.config;

import hu.perit.spvitamin.core.util.Case;import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Loads action-level authorization rules from application.yml.
 *
 * <p>Two equivalent configuration formats are supported:
 *
 * <p><b>Legacy format</b> ({@code ngface.rolemap}) — grouped by role:
 * <pre>
 * ngface:
 *   rolemap:
 *     ROLE_ADMIN:
 *       - "TransferPackageItemTableRestController:FINALIZE"
 *       - "TransferPackageItemTableRestController:APPROVE"
 *     ROLE_APPROVER:
 *       - "TransferPackageItemTableRestController:APPROVE"
 * </pre>
 *
 * <p><b>New format</b> ({@code ngface.actionmap}) — grouped by controller (preferred):
 * <pre>
 * ngface:
 *   actionmap:
 *     TransferPackageItemTableRestController:
 *       FINALIZE: "ROLE_ADMIN"
 *       APPROVE:  "ROLE_ADMIN, ROLE_APPROVER"
 * </pre>
 *
 * <p>Both formats can be used simultaneously; their entries are merged.
 * When an action is associated with multiple roles the authorization check uses OR logic —
 * the user needs to have at least one of the listed roles.
 *
 * <p>At startup an inverted index is built: "ControllerName:ACTION" → list of role names,
 * so lookups are O(1).
 */
@Data
@Configuration
@ConfigurationProperties("ngface")
@Slf4j
public class ActionPermissionsProperties
{
    /**
     * Legacy format: ROLE_NAME → list of "ControllerSimpleName:ACTION_ID" entries.
     */
    private Map<String, List<String>> rolemap = new LinkedHashMap<>();

    /**
     * New format: ControllerSimpleName → (ACTION_ID → comma-separated role names).
     */
    private Map<String, Map<String, String>> actionmap = new LinkedHashMap<>();

    /**
     * Inverted index built at startup: action key → list of required roles (OR logic).
     */
    private final Map<String, List<String>> index = new HashMap<>();


    @PostConstruct
    public void buildIndex()
    {
        rolemap.forEach((roleName, actions) ->
        {
            for (String controllerAction : actions)
            {
                index.computeIfAbsent(Case.toLower(controllerAction), k -> new ArrayList<>()).add(roleName);
            }
        });

        actionmap.forEach((controllerName, actions) ->
            actions.forEach((actionId, rolesStr) ->
            {
                List<String> roles = Arrays.stream(rolesStr.split(","))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .collect(Collectors.toList());
                index.computeIfAbsent(Case.toLower(controllerName + ":" + actionId), k -> new ArrayList<>()).addAll(roles);
            })
        );

        log.info("ActionPermissionsProperties: {} action rules loaded", index.size());
    }


    public Optional<List<String>> getRequiredRoles(String controllerSimpleName, String actionId)
    {
        List<String> roles = index.get(Case.toLower(controllerSimpleName + ":" + actionId));
        return (roles == null || roles.isEmpty()) ? Optional.empty() : Optional.of(roles);
    }
}
