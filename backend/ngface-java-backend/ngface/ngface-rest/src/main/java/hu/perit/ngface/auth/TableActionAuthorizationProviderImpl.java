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

package hu.perit.ngface.auth;

import hu.perit.ngface.config.ActionPermissionsProperties;
import hu.perit.ngface.rest.TableActionAuthorizationProvider;
import hu.perit.spvitamin.spring.exception.AuthorizationException;
import hu.perit.spvitamin.spring.security.AuthenticatedUser;
import hu.perit.spvitamin.spring.security.auth.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

/**
 * Evaluates action-level authorization rules defined in {@code ngface.rolemap} or
 * {@code ngface.actionmap} in application.yml.
 *
 * <p>Called by {@code NgfaceTableRestController.onActionClick} before dispatching to the
 * component controller. If the action is not listed in the configuration, access is denied.
 * When multiple roles are configured for an action, the user needs to have at least one of them.
 */
@Service
@RequiredArgsConstructor
public class TableActionAuthorizationProviderImpl implements TableActionAuthorizationProvider
{
    private final ActionPermissionsProperties actionPermissionsProperties;
    private final AuthorizationService authorizationService;


    @Override
    public void authorize(String controllerSimpleName, String actionId)
    {
        List<String> requiredRoles = this.actionPermissionsProperties
                .getRequiredRoles(controllerSimpleName, actionId)
                .orElseThrow(() -> new AuthorizationException(getErrorMessage(controllerSimpleName, actionId)));

        AuthenticatedUser authenticatedUser = this.authorizationService.getAuthenticatedUser();
        if (requiredRoles.stream().noneMatch(authenticatedUser::hasRole))
        {
            throw new AuthorizationException(getErrorMessage(controllerSimpleName, actionId));
        }
    }


    private static String getErrorMessage(String controllerSimpleName, String actionId)
    {
        return MessageFormat.format("Insufficient permissions for action: {0}:{1}!", controllerSimpleName, actionId);
    }
}
