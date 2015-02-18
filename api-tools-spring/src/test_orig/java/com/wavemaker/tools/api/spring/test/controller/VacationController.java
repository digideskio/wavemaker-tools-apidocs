/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.api.spring.test.controller;

// Generated 19 Nov, 2014 12:15:13 PM


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
import com.wavemaker.tools.api.spring.test.Vacation;
import com.wavemaker.tools.api.spring.test.service.VacationService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;


/**
 * Controller object for domain model class Vacation.
 * @see com.wavemaker.tools.api.spring.test.Vacation
 */

@RestController
@Api(value = "/hrdb/Vacation", description = "Exposes APIs to work with Vacation resource.")
@RequestMapping("/hrdb/Vacation")
public class VacationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(VacationController.class);

	@Autowired
	@Qualifier("hrdb.VacationService")
	private VacationService service;


	@RequestMapping(value = "/search", method = RequestMethod.POST)
	@ApiOperation(value = "Returns the list of Vacation instances matching the search criteria.")
	public Page<Vacation> findAll( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
		LOGGER.debug("Rendering Vacations list");
		return service.findAll(queryFilters, pageable);
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ApiOperation(value = "Returns the list of Vacation instances.")
	public Page<Vacation> getVacations(Pageable pageable) {
		LOGGER.debug("Rendering Vacations list");
		return service.findAll(pageable);
	}

	@RequestMapping(value = "/count", method = RequestMethod.GET)
	@ApiOperation(value = "Returns the total count of Vacation instances.")
	public Long countAll() {
		LOGGER.debug("counting Vacations");
		Long count = service.countAll();
		return count;
	}


    @RequestMapping(value = "/{id:.+}", method = RequestMethod.GET)
    @ApiOperation(value = "Returns the Vacation instance associated with the given id.")
    public Vacation getVacation(@PathVariable("id") Integer id) throws EntityNotFoundException {
        LOGGER.debug("Getting Vacation with id: {}" , id);
        Vacation instance = service.findById(id);
        LOGGER.debug("Vacation details with id: {}" , instance);
        return instance;
    }

    @RequestMapping(value = "/{id:.+}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Deletes the Vacation instance associated with the given id.")
    public boolean delete(@PathVariable("id") Integer id) throws EntityNotFoundException {
        LOGGER.debug("Deleting Vacation with id: {}" , id);
        Vacation deleted = service.delete(id);
        return deleted != null;
    }

    @RequestMapping(value = "/{id:.+}", method = RequestMethod.PUT)
    @ApiOperation(value = "Updates the Vacation instance associated with the given id.")
    public Vacation editVacation(@PathVariable("id") Integer id, @RequestBody Vacation instance) throws EntityNotFoundException {
        LOGGER.debug("Editing Vacation with id: {}" , instance.getId());
        instance.setId(id);
        instance = service.update(instance);
        LOGGER.debug("Vacation details with id: {}" , instance);
        return instance;
    }

    @RequestMapping(value = "/{id:.+}", method = RequestMethod.PUT, consumes = {"multipart/form-data"})
    @ApiOperation(value = "Updates the Vacation instance associated with the given id.This API should be used when Vacation instance fields that require multipart data.")
    public Vacation editVacation(@PathVariable("id") Integer id, MultipartHttpServletRequest multipartHttpServletRequest) throws EntityNotFoundException {
        Vacation vacation = WMMultipartUtils.toObject(multipartHttpServletRequest,Vacation.class);
        vacation.setId(id);
        LOGGER.debug("Creating a new vacation with information: {}" , vacation);
        return service.update(vacation);
    }



	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ApiOperation(value = "Creates a new Vacation instance.")
	public Vacation createVacation(@RequestBody Vacation instance) {
		LOGGER.debug("Create Vacation with information: {}" , instance);
		instance = service.create(instance);
		LOGGER.debug("Created Vacation with information: {}" , instance);
	    return instance;
	}

	@RequestMapping(value = "/", method = RequestMethod.POST, consumes = {"multipart/form-data"})
	@ApiOperation(value = "Creates a new Vacation instance.This API should be used when the Vacation instance has fields that requires multipart data.")
    public Vacation createVacation(MultipartHttpServletRequest multipartHttpServletRequest) {
    	Vacation vacation = WMMultipartUtils.toObject(multipartHttpServletRequest,Vacation.class);
        LOGGER.debug("Creating a new vacation with information: {}" , vacation);
        return service.create(vacation);
    }
	
	/**
	 * This setter method should only be used by unit tests
	 * 
	 * @param service
	 */
	protected void setVacationService(VacationService service) {
		this.service = service;
	}
}

