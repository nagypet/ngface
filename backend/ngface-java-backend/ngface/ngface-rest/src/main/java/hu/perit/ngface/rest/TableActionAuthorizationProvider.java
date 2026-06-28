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

package hu.perit.ngface.rest;

/**
 * Hook for action-level authorization in {@link NgfaceTableRestController#onActionClick}.
 * <p>
 * Implement this interface as a Spring bean to authorize table actions declaratively
 * (e.g. driven by configuration). The implementation should throw a runtime exception
 * to deny access; returning normally means the action is authorized.
 */
public interface TableActionAuthorizationProvider
{
    /**
     * @param controllerSimpleName simple class name of the REST controller (e.g. "MyClientsTableRestController")
     * @param actionId             action identifier from the request
     * @throws RuntimeException if the action is not authorized
     */
    void authorize(String controllerSimpleName, String actionId);
}
