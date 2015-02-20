/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */

package com.wavemaker.tools.apidocs.tools.springservice;
// Generated 19 Nov, 2014 12:15:14 PM 

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wavemaker.runtime.data.model.CustomQuery;

public interface HrdbQueryExecutorService {

	
	Page<Object> executeWMCustomQuerySelect(CustomQuery query, Pageable pageable) ;

	int executeWMCustomQueryUpdate(CustomQuery query) ;
}

