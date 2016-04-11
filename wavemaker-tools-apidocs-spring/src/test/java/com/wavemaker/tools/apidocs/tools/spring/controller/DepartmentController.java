/**
 * Copyright © 2013 - 2016 WaveMaker, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wavemaker.tools.apidocs.tools.spring.controller;

// Generated 19 Nov, 2014 12:15:13 PM


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.wavemaker.tools.apidocs.tools.spring.Department;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;


/**
 * Controller object for domain model class Department.
 */

@RestController
@Api(value = "/hrdb/Department", description = "Exposes APIs to work with Department resource.")
@RequestMapping("/hrdb/Department")
public class DepartmentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ApiOperation(value = "Returns the list of Department instances matching the search criteria.")
    public Page<Department> findAll(Pageable pageable) {
        LOGGER.debug("Rendering Departments list");
        return null;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiOperation(value = "Returns the list of Department instances.")
    public Page<Department> getDepartments(Pageable pageable) {
        LOGGER.debug("Rendering Departments list");
        return null;
    }

    @RequestMapping(value = "/count2", method = RequestMethod.GET)
    @ApiOperation(value = "Returns the total count of Department instances.")
    public Long countAll(@RequestParam("countId") String countId, @RequestParam("countId2") String countId2) {
        LOGGER.debug("counting Departments");
        return null;
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    @ApiOperation(value = "Returns the total count of Department instances.")
    public Long countAll() {
        LOGGER.debug("counting Departments");
        return null;
    }

    @RequestMapping(value = "/count1", method = RequestMethod.GET)
    @ApiOperation(value = "Returns the total count of Department instances.")
    public Long countAll(@RequestParam("countId") String countId) {
        LOGGER.debug("counting Departments");
        return null;
    }


    @RequestMapping(value = "/{id:.+}", method = RequestMethod.GET)
    @ApiOperation(value = "Returns the Department instance associated with the given id.")
    public Department getDepartment(@PathVariable("id") Integer id) {
        LOGGER.debug("Getting Department with id: {}", id);
        return null;
    }

    @RequestMapping(value = "/{id:.+}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Deletes the Department instance associated with the given id.")
    public boolean delete(@PathVariable("id") Integer id) {
        LOGGER.debug("Deleting Department with id: {}", id);
        return true;
    }

    @RequestMapping(value = "/{id:.+}", method = RequestMethod.PUT)
    @ApiOperation(value = "Updates the Department instance associated with the given id.")
    public Department editDepartment(@PathVariable("id") Integer id, @RequestBody Department instance) {
        return null;
    }

    @RequestMapping(value = "/{id:.+}/upload", method = RequestMethod.PUT, consumes = {"multipart/form-data"})
    @ApiOperation(value = "Updates the Department instance associated with the given id.This API should be used when Department instance fields that require multipart data.")
    public Department editDepartment(
            @PathVariable("id") Integer id, MultipartHttpServletRequest multipartHttpServletRequest) {
        return null;
    }


    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ApiOperation(value = "Creates a new Department instance.")
    public Department createDepartment(@RequestBody Department instance) {
        LOGGER.debug("Create Department with information: {}", instance);
        return null;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = {"multipart/form-data"})
    @ApiOperation(value = "Creates a new Department instance.This API should be used when the Department instance has fields that requires multipart data.")
    public Department createDepartment(MultipartHttpServletRequest multipartHttpServletRequest) {
        return null;
    }

    @RequestMapping(value = "/create2", method = RequestMethod.POST, consumes = {"multipart/form-data"})
    @ApiOperation(value = "Creates a new Department instance.This API should be used when the Department instance has fields that requires multipart data.")
    public Department createDepartment(@RequestParam("departmentJson") String department) {
        return null;
    }

}

