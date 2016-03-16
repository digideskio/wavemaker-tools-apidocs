/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.spring.controller;

// Generated 19 Nov, 2014 12:15:13 PM


import java.sql.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wavemaker.tools.apidocs.tools.spring.Vacation;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;


/**
 * Controller object for domain model class Vacation.
 *
 * @see Vacation
 */

@RestController
@Api(value = "/hrdb/Vacation", description = "Exposes APIs to work with Vacation resource.")
@RequestMapping("/hrdb/Vacation")
public class VacationController {

   /* *//**
     * Method for testing Request part.
     *//*

    @RequestMapping(value = "/sampleJavaOperation", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST, consumes = "multipart/form-data")
    public String sampleJavaOperation(@RequestPart("emp") Employee emp, @RequestPart("dept") Department dept) {
        return null;
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ApiOperation(value = "Returns the list of Vacation instances matching the search criteria.")
    public Page<Vacation> findAll(Pageable pageable) {
        return null;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiOperation(value = "Returns the list of Vacation instances.")
    public Page<Vacation> getVacations(Pageable pageable) {
        return null;
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    @ApiOperation(value = "Returns the total count of Vacation instances.")
    public Long countAll() {
        return null;
    }


    @RequestMapping(value = "/{id:.+}", method = RequestMethod.GET)
    @ApiOperation(value = "Returns the Vacation instance associated with the given id.")
    public Vacation getVacation(@PathVariable("id") Integer id) throws EntityNotFoundException {
        return null;
    }

    @RequestMapping(value = "/{id:.+}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Deletes the Vacation instance associated with the given id.")
    public boolean delete(@PathVariable("id") Integer id) throws EntityNotFoundException {
        return true;
    }

    @RequestMapping(value = "/{id:.+}", method = RequestMethod.PUT)
    @ApiOperation(value = "Updates the Vacation instance associated with the given id.")
    public Vacation editVacation(
            @PathVariable("id") Integer id, @RequestBody Vacation instance) throws EntityNotFoundException {
        return null;
    }

    @RequestMapping(value = "/{id:.+}", method = RequestMethod.PUT, consumes = {"multipart/form-data"})
    @ApiOperation(value = "Updates the Vacation instance associated with the given id.This API should be used when Vacation instance fields that require multipart data.")
    public Vacation editVacation(
            @PathVariable("id") Integer id,
            MultipartHttpServletRequest multipartHttpServletRequest) throws EntityNotFoundException {
        return null;
    }


    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ApiOperation(value = "Creates a new Vacation instance.")
    public Vacation createVacation(@RequestBody Vacation instance) {
        return null;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = {"multipart/form-data"})
    @ApiOperation(value = "Creates a new Vacation instance.This API should be used when the Vacation instance has fields that requires multipart data.")
    public Vacation createVacation(MultipartHttpServletRequest multipartHttpServletRequest) {
        return null;
    }*/

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ApiOperation(value = "Creates a new Vacation instance.")
    public Vacation searchVacations(@RequestParam("date") Date date) {
        return null;
    }

}

