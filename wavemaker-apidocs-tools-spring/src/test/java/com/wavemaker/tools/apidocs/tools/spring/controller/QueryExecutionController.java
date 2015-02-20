/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */

package com.wavemaker.tools.apidocs.tools.spring.controller;
// Generated 19 Nov, 2014 12:15:14 PM 

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wavemaker.runtime.data.model.CustomQuery;
import com.wavemaker.tools.apidocs.tools.springservice.HrdbQueryExecutorService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController(value = "Hrdb.QueryExecutionController")
@Api(value = "/hrdb/queryExecutor", description = "Controller class for query execution")
@RequestMapping("/hrdb/queryExecutor")
public class QueryExecutionController {

	private static final Logger LOGGER = LoggerFactory.getLogger(QueryExecutionController.class);

	@Autowired
	private HrdbQueryExecutorService queryService;
	
	

	@RequestMapping(value = "/queries/wm_custom", method = RequestMethod.POST)
	@ApiOperation(value = "Process request to execute customer queries")
	public Page<Object> executeWMCustomQuery(@RequestBody CustomQuery query, Pageable pageable) {
		Page<Object> result = queryService.executeWMCustomQuerySelect(query, pageable);
		LOGGER.debug("got the result {}" + result);
		return result;
	}

	@RequestMapping(value = "/queries/wm_custom_update", method = RequestMethod.POST)
	@ApiOperation(value = "Process request to execute customer queries")
    public int executeWMCustomQuery(@RequestBody CustomQuery query) {
        int result = queryService.executeWMCustomQueryUpdate(query);
        LOGGER.debug("got the result {}" + result);
        return result;
    }

}

