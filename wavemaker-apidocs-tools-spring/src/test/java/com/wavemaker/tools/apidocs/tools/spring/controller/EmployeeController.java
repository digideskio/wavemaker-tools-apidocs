/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.spring.controller;

// Generated 19 Nov, 2014 12:15:13 PM


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.wavemaker.runtime.data.exception.EntityNotFoundException;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.util.WMMultipartUtils;
import com.wavemaker.tools.apidocs.tools.spring.Employee;
import com.wavemaker.tools.apidocs.tools.springservice.EmployeeService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;


/**
 * Controller object for domain model class Employee.
 * @see com.wavemaker.tools.api.spring.test.Employee
 */

@RestController
@Api(value = "/hrdb/Employee", description = "Exposes APIs to work with Employee resource.")
@RequestMapping("/hrdb/Employee")
public class EmployeeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	@Qualifier("hrdb.EmployeeService")
	private EmployeeService service;


	@RequestMapping(value = "/search", method = RequestMethod.POST)
	@ApiOperation(value = "Returns the list of Employee instances matching the search criteria.")
	public Page<Employee> findAll( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
		LOGGER.debug("Rendering Employees list");
		return service.findAll(queryFilters, pageable);
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ApiOperation(value = "Returns the list of Employee instances.")
	public Page<Employee> getEmployees(Pageable pageable) {
		LOGGER.debug("Rendering Employees list");
		return service.findAll(pageable);
	}

	@RequestMapping(value = "/count", method = RequestMethod.GET)
	@ApiOperation(value = "Returns the total count of Employee instances.")
	public Long countAll() {
		LOGGER.debug("counting Employees");
		Long count = service.countAll();
		return count;
	}


    @RequestMapping(value = "/{id:.+}", method = RequestMethod.GET)
    @ApiOperation(value = "Returns the Employee instance associated with the given id.")
    public Employee getEmployee(@PathVariable("id") Integer id) throws EntityNotFoundException {
        LOGGER.debug("Getting Employee with id: {}" , id);
        Employee instance = service.findById(id);
        LOGGER.debug("Employee details with id: {}" , instance);
        return instance;
    }

    @RequestMapping(value = "/{id:.+}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Deletes the Employee instance associated with the given id.")
    public boolean delete(@PathVariable("id") Integer id) throws EntityNotFoundException {
        LOGGER.debug("Deleting Employee with id: {}" , id);
        Employee deleted = service.delete(id);
        return deleted != null;
    }

    @RequestMapping(value = "/{id:.+}", method = RequestMethod.PUT)
    @ApiOperation(value = "Updates the Employee instance associated with the given id.")
    public Employee editEmployee(@PathVariable("id") Integer id, @RequestBody Employee instance) throws EntityNotFoundException {
        LOGGER.debug("Editing Employee with id: {}" , instance.getEid());
        instance.setEid(id);
        instance = service.update(instance);
        LOGGER.debug("Employee details with id: {}" , instance);
        return instance;
    }

    @RequestMapping(value = "/{id:.+}", method = RequestMethod.PUT, consumes = {"multipart/form-data"})
    @ApiOperation(value = "Updates the Employee instance associated with the given id.This API should be used when Employee instance fields that require multipart data.")
    public Employee editEmployee(@PathVariable("id") Integer id, MultipartHttpServletRequest multipartHttpServletRequest) throws EntityNotFoundException {
        Employee employee = WMMultipartUtils.toObject(multipartHttpServletRequest,Employee.class);
        employee.setEid(id);
        LOGGER.debug("Creating a new employee with information: {}" , employee);
        return service.update(employee);
    }



	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ApiOperation(value = "Creates a new Employee instance.")
	public Employee createEmployee(@RequestBody Employee instance) {
		LOGGER.debug("Create Employee with information: {}" , instance);
		instance = service.create(instance);
		LOGGER.debug("Created Employee with information: {}" , instance);
	    return instance;
	}

    @RequestMapping(value = "/multi", method = RequestMethod.POST)
    @ApiOperation(value = "Creates a new Employee instance.")
    public List<Employee> createEmployees(@RequestBody List<Employee> instance) {
        LOGGER.debug("Create Employees with information: {}", instance);
//        instance = service.create(instance);
        LOGGER.debug("Created Employees with information: {}", instance);
        return instance;
    }

	@RequestMapping(value = "/", method = RequestMethod.POST, consumes = {"multipart/form-data"})
	@ApiOperation(value = "Creates a new Employee instance.This API should be used when the Employee instance has fields that requires multipart data.")
    public Employee createEmployee(MultipartHttpServletRequest multipartHttpServletRequest) {
    	Employee employee = WMMultipartUtils.toObject(multipartHttpServletRequest,Employee.class);
        LOGGER.debug("Creating a new employee with information: {}" , employee);
        return service.create(employee);
    }
	
	/**
	 * This setter method should only be used by unit tests
	 * 
	 * @param service
	 */
	protected void setEmployeeService(EmployeeService service) {
		this.service = service;
	}
}

