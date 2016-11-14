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
package com.test.returnsas;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wavemaker.tools.api.core.annotations.ReturnsAs;
import com.wavemaker.tools.apidocs.tools.spring.Employee;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 14/11/16
 */
@RestController
@Api(value = "/resolver/ReturnsAs", description = "Exposes APIs to work with ReturnsAs resource.")
@RequestMapping("/resolver/ReturnsAs")
public class ModelAsController {

    @RequestMapping(value = "/v1", method = RequestMethod.GET)
    @ApiOperation(value = "Returns the list of Employee instances.")
    public
    @ReturnsAs(value = Page.class, typeArguments = {Employee.class})
    Page<Object> getEmployees(Pageable pageable) {
        return null;
    }

    @RequestMapping(value = "/v1/{id:.+}", method = RequestMethod.GET)
    @ApiOperation(value = "Returns the Employee instance associated with the given id.")
    public
    @ReturnsAs(Employee.class)
    Object getEmployeeV1(@PathVariable("id") Integer id) throws EntityNotFoundException {
        return null;
    }

    @RequestMapping(value = "/v2", method = RequestMethod.GET)
    @ApiOperation(value = "Returns the list of Employee instances.")
    @ReturnsAs(value = Page.class, typeArguments = {Employee.class})
    public Page<Object> getEmployeesV2(Pageable pageable) {
        return null;
    }

    @RequestMapping(value = "/v2/{id:.+}", method = RequestMethod.GET)
    @ApiOperation(value = "Returns the Employee instance associated with the given id.")
    @ReturnsAs(Employee.class)
    public Object getEmployeeV2(@PathVariable("id") Integer id) throws EntityNotFoundException {
        return null;
    }

    @RequestMapping(value = "/v3", method = RequestMethod.GET)
    @ApiOperation(value = "Returns the list of Employee instances.")
    public Page<Object> getEmployeesV3(Pageable pageable) {
        return null;
    }

    @RequestMapping(value = "/v3/{id:.+}", method = RequestMethod.GET)
    @ApiOperation(value = "Returns the Employee instance associated with the given id.")
    public Object getEmployeeV3(@PathVariable("id") Integer id) throws EntityNotFoundException {
        return null;
    }
}
