/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */

package com.wavemaker.tools.api.spring.test.service;
// Generated 19 Nov, 2014 12:15:14 PM 


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wavemaker.runtime.data.dao.query.WMQueryExecutor;
import com.wavemaker.runtime.data.model.CustomQuery;

@Service
public class HrdbQueryExecutorServiceImpl implements HrdbQueryExecutorService {
	private static final Logger LOGGER = LoggerFactory.getLogger(HrdbQueryExecutorServiceImpl.class);

	@Autowired
	@Qualifier("hrdbWMQueryExecutor")
	private WMQueryExecutor queryExecutor;


	@Transactional(value = "hrdbTransactionManager")
	@Override
	public Page<Object> executeWMCustomQuerySelect(CustomQuery query, Pageable pageable) {
	    return queryExecutor.executeCustomQuery(query, pageable);
	}

	@Transactional(value = "hrdbTransactionManager")
    @Override
    public int executeWMCustomQueryUpdate(CustomQuery query) {
        return queryExecutor.executeCustomQueryForUpdate(query);
    }
}

