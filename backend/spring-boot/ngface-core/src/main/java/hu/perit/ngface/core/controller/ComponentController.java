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

package hu.perit.ngface.core.controller;

import hu.perit.ngface.core.data.TableActionParams;
import lombok.NonNull;

/**
 * @param <T>
 * @author Peter Nagy
 */
public interface ComponentController<P, T>
{
    T initializeData(@NonNull P params);

    /**
     * This function will be called by the framework whenever new data has been sumbitted by the frontend.
     *
     * @param data
     */
    void onSave(T data);


    /**
     *
     * @param tableActionParams
     */
    void onActionClick(TableActionParams tableActionParams) throws Exception;
}
