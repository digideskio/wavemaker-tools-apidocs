/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.springservice;
// Generated 19 Nov, 2014 12:15:13 PM


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wavemaker.runtime.data.dao.WMGenericDao;
import com.wavemaker.runtime.data.exception.EntityNotFoundException;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.tools.apidocs.tools.spring.Department;


/**
 * ServiceImpl object for domain model class Department.
 * @see com.wavemaker.tools.api.spring.test.Department
 */
@Service("hrdb.DepartmentService")
public class DepartmentServiceImpl implements DepartmentService {


    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentServiceImpl.class);

    @Autowired
    @Qualifier("hrdb.DepartmentDao")
    private WMGenericDao<Department, Integer> wmGenericDao;
    public void setWMGenericDao(WMGenericDao<Department, Integer> wmGenericDao){
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "hrdbTransactionManager")
    @Override
    public Department create(Department department) {
        LOGGER.debug("Creating a new department with information: {}" , department);
        return this.wmGenericDao.create(department);
    }

    @Transactional(rollbackFor = EntityNotFoundException.class, value = "hrdbTransactionManager")
    @Override
    public Department delete(Integer departmentId) throws EntityNotFoundException {
        LOGGER.debug("Deleting department with id: {}" , departmentId);
        Department deleted = this.wmGenericDao.findById(departmentId);
        if (deleted == null) {
            LOGGER.debug("No department found with id: {}" , departmentId);
            throw new EntityNotFoundException(String.valueOf(departmentId));
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(readOnly = true, value = "hrdbTransactionManager")
    @Override
    public Page<Department> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all departments");
        return this.wmGenericDao.search(queryFilters, pageable);
    }
    
    @Transactional(readOnly = true, value = "hrdbTransactionManager")
    @Override
    public Page<Department> findAll(Pageable pageable) {
        LOGGER.debug("Finding all departments");
        return this.wmGenericDao.search(null, pageable);
    }

    @Transactional(readOnly = true, value = "hrdbTransactionManager")
    @Override
    public Department findById(Integer id) throws EntityNotFoundException {
        LOGGER.debug("Finding department by id: {}" , id);
        Department department=this.wmGenericDao.findById(id);
        if(department==null){
            LOGGER.debug("No department found with id: {}" , id);
            throw new EntityNotFoundException(String.valueOf(id));
        }
        return department;
    }

    @Transactional(rollbackFor = EntityNotFoundException.class, value = "hrdbTransactionManager")
    @Override
    public Department update(Department updated) throws EntityNotFoundException {
        LOGGER.debug("Updating department with information: {}" , updated);
        this.wmGenericDao.update(updated);
        return this.wmGenericDao.findById((Integer)updated.getDeptid());
    }

    @Transactional(readOnly = true, value = "hrdbTransactionManager")
    @Override
    public long countAll() {
        return this.wmGenericDao.count();
    }
}


