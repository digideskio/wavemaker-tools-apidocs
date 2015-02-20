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
import com.wavemaker.tools.apidocs.tools.spring.Employee;


/**
 * ServiceImpl object for domain model class Employee.
 * @see com.wavemaker.tools.api.spring.test.Employee
 */
@Service("hrdb.EmployeeService")
public class EmployeeServiceImpl implements EmployeeService {


    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    @Qualifier("hrdb.EmployeeDao")
    private WMGenericDao<Employee, Integer> wmGenericDao;
    public void setWMGenericDao(WMGenericDao<Employee, Integer> wmGenericDao){
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "hrdbTransactionManager")
    @Override
    public Employee create(Employee employee) {
        LOGGER.debug("Creating a new employee with information: {}" , employee);
        return this.wmGenericDao.create(employee);
    }

    @Transactional(rollbackFor = EntityNotFoundException.class, value = "hrdbTransactionManager")
    @Override
    public Employee delete(Integer employeeId) throws EntityNotFoundException {
        LOGGER.debug("Deleting employee with id: {}" , employeeId);
        Employee deleted = this.wmGenericDao.findById(employeeId);
        if (deleted == null) {
            LOGGER.debug("No employee found with id: {}" , employeeId);
            throw new EntityNotFoundException(String.valueOf(employeeId));
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(readOnly = true, value = "hrdbTransactionManager")
    @Override
    public Page<Employee> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all employees");
        return this.wmGenericDao.search(queryFilters, pageable);
    }
    
    @Transactional(readOnly = true, value = "hrdbTransactionManager")
    @Override
    public Page<Employee> findAll(Pageable pageable) {
        LOGGER.debug("Finding all employees");
        return this.wmGenericDao.search(null, pageable);
    }

    @Transactional(readOnly = true, value = "hrdbTransactionManager")
    @Override
    public Employee findById(Integer id) throws EntityNotFoundException {
        LOGGER.debug("Finding employee by id: {}" , id);
        Employee employee=this.wmGenericDao.findById(id);
        if(employee==null){
            LOGGER.debug("No employee found with id: {}" , id);
            throw new EntityNotFoundException(String.valueOf(id));
        }
        return employee;
    }

    @Transactional(rollbackFor = EntityNotFoundException.class, value = "hrdbTransactionManager")
    @Override
    public Employee update(Employee updated) throws EntityNotFoundException {
        LOGGER.debug("Updating employee with information: {}" , updated);
        this.wmGenericDao.update(updated);
        return this.wmGenericDao.findById((Integer)updated.getEid());
    }

    @Transactional(readOnly = true, value = "hrdbTransactionManager")
    @Override
    public long countAll() {
        return this.wmGenericDao.count();
    }
}


