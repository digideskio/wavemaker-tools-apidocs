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
import com.wavemaker.tools.api.spring.test.User;
import com.wavemaker.tools.api.spring.test.service.UserService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;


/**
 * Controller object for domain model class User.
 * @see com.wavemaker.tools.api.spring.test.User
 */

@RestController
@Api(value = "/hrdb/User", description = "Exposes APIs to work with User resource.")
@RequestMapping("/hrdb/User")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	@Qualifier("hrdb.UserService")
	private UserService service;


	@RequestMapping(value = "/search", method = RequestMethod.POST)
	@ApiOperation(value = "Returns the list of User instances matching the search criteria.")
	public Page<User> findAll( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
		LOGGER.debug("Rendering Users list");
		return service.findAll(queryFilters, pageable);
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ApiOperation(value = "Returns the list of User instances.")
	public Page<User> getUsers(Pageable pageable) {
		LOGGER.debug("Rendering Users list");
		return service.findAll(pageable);
	}

	@RequestMapping(value = "/count", method = RequestMethod.GET)
	@ApiOperation(value = "Returns the total count of User instances.")
	public Long countAll() {
		LOGGER.debug("counting Users");
		Long count = service.countAll();
		return count;
	}


    @RequestMapping(value = "/{id:.+}", method = RequestMethod.GET)
    @ApiOperation(value = "Returns the User instance associated with the given id.")
    public User getUser(@PathVariable("id") Integer id) throws EntityNotFoundException {
        LOGGER.debug("Getting User with id: {}" , id);
        User instance = service.findById(id);
        LOGGER.debug("User details with id: {}" , instance);
        return instance;
    }

    @RequestMapping(value = "/{id:.+}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Deletes the User instance associated with the given id.")
    public boolean delete(@PathVariable("id") Integer id) throws EntityNotFoundException {
        LOGGER.debug("Deleting User with id: {}" , id);
        User deleted = service.delete(id);
        return deleted != null;
    }

    @RequestMapping(value = "/{id:.+}", method = RequestMethod.PUT)
    @ApiOperation(value = "Updates the User instance associated with the given id.")
    public User editUser(@PathVariable("id") Integer id, @RequestBody User instance) throws EntityNotFoundException {
        LOGGER.debug("Editing User with id: {}" , instance.getUserid());
        instance.setUserid(id);
        instance = service.update(instance);
        LOGGER.debug("User details with id: {}" , instance);
        return instance;
    }

    @RequestMapping(value = "/{id:.+}", method = RequestMethod.PUT, consumes = {"multipart/form-data"})
    @ApiOperation(value = "Updates the User instance associated with the given id.This API should be used when User instance fields that require multipart data.")
    public User editUser(@PathVariable("id") Integer id, MultipartHttpServletRequest multipartHttpServletRequest) throws EntityNotFoundException {
        User user = WMMultipartUtils.toObject(multipartHttpServletRequest,User.class);
        user.setUserid(id);
        LOGGER.debug("Creating a new user with information: {}" , user);
        return service.update(user);
    }



	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ApiOperation(value = "Creates a new User instance.")
	public User createUser(@RequestBody User instance) {
		LOGGER.debug("Create User with information: {}" , instance);
		instance = service.create(instance);
		LOGGER.debug("Created User with information: {}" , instance);
	    return instance;
	}

	@RequestMapping(value = "/", method = RequestMethod.POST, consumes = {"multipart/form-data"})
	@ApiOperation(value = "Creates a new User instance.This API should be used when the User instance has fields that requires multipart data.")
    public User createUser(MultipartHttpServletRequest multipartHttpServletRequest) {
    	User user = WMMultipartUtils.toObject(multipartHttpServletRequest,User.class);
        LOGGER.debug("Creating a new user with information: {}" , user);
        return service.create(user);
    }
	
	/**
	 * This setter method should only be used by unit tests
	 * 
	 * @param service
	 */
	protected void setUserService(UserService service) {
		this.service = service;
	}
}

