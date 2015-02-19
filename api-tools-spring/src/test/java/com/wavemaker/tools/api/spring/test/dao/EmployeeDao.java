/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.api.spring.test.dao;
// Generated 19 Nov, 2014 12:15:13 PM 


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.wavemaker.runtime.data.dao.WMGenericDaoImpl;
import com.wavemaker.tools.api.spring.test.Employee;
/**
 * Specifies methods used to obtain and modify Employee related information
 * which is stored in the database.
 */
@Repository("hrdb.EmployeeDao")
public class EmployeeDao extends WMGenericDaoImpl <Employee, Integer> {

   @Autowired
   @Qualifier("hrdbTemplate")
   private HibernateTemplate template;

   public HibernateTemplate getTemplate() {
        return this.template;
   }
}

