/**
 * Copyright © 2013 - 2016 WaveMaker, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wavemaker.tools.apidocs.tools.parser.parser;

import com.wavemaker.tools.apidocs.tools.core.model.Operation;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 10/11/14
 */
public interface MethodParser extends BaseParser<Operation> {

    /**
     * @return true if it is rest method.
     */
    boolean isRestMethod();

    /**
     * It will read the method and extracts the path of that method. This is use full separating endpoints.
     *
     * @return Path for given method.
     */
    String[] getPaths();

    /**
     * It will read the method and extracts the path of that method.
     *
     * @return Http Method
     */
    String[] getHttpMethods();
}
