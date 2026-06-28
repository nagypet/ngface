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

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Loads the {@code mebil-service.action-permissions} section from application.yml.
 *
 * <p>Structure in YAML:
 * <pre>
 * ngface:
 *   rolemap:
 *     ROLE_ADMIN:
 *       - "TransferPackageItemTableRestController:FINALIZE"
 *       - "TransferPackageItemTableRestController:REOPEN"
 *     ROLE_APPROVER:
 *       - "TransferPackageItemTableRestController:APPROVE"
 * </pre>
 *
 * <p>At startup an inverted index is built: "ControllerName:ACTION" → role name,
 * so lookups are O(1).
 */
@Data
@Configuration
@ConfigurationProperties("ngface")
@Slf4j
public class ActionPermissionsProperties
{
    /**
     * ROLE_NAME → list of "ControllerSimpleName:ACTION_ID" entries
     */
    private Map<String, List<String>> rolemap = new LinkedHashMap<>();

    /**
     * Inverted index built at startup.
     */
    private final Map<String, String> index = new HashMap<>();


    @PostConstruct
    public void buildIndex()
    {
        rolemap.forEach((roleName, actions) ->
        {
            for (String controllerAction : actions)
            {
                String previous = index.put(Case.toLower(controllerAction), roleName);
                if (previous != null)
                {
                    log.warn("Duplicate action-permissions entry '{}': was {}, now {}", controllerAction, previous, roleName);
                }
            }
        });
        log.info("ActionPermissionsProperties: {} action rules loaded", index.size());
    }


    public Optional<String> getRequiredRole(String controllerSimpleName, String actionId)
    {
        return Optional.ofNullable(index.get(Case.toLower(controllerSimpleName + ":" + actionId)));
    }
}
