/**
 * Copyright (c) 2013 - 2014 WaveMaker, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker, Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker, Inc.
 */
package com.wavemaker.tools.apidocs.tools.springservice;
// Generated 19 Nov, 2014 12:15:13 PM


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wavemaker.runtime.data.exception.EntityNotFoundException;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.tools.apidocs.tools.spring.Employee;

/**
 * Service object for domain model class Employee.
 * @see com.wavemaker.tools.api.spring.test.Employee
 */

public interface EmployeeService {

   /**
	 * Creates a new employee.
	 * 
	 * @param created
	 *            The information of the created employee.
	 * @return The created employee.
	 */
	public Employee create(Employee created);

	/**
	 * Deletes a employee.
	 * 
	 * @param employeeId
	 *            The id of the deleted employee.
	 * @return The deleted employee.
	 * @throws EntityNotFoundException
	 *             if no employee is found with the given id.
	 */
	public Employee delete(Integer employeeId) throws EntityNotFoundException;

	/**
	 * Finds all employees.
	 * 
	 * @return A list of employees.
	 */
	public Page<Employee> findAll(QueryFilter[] queryFilters, Pageable pageable);
	
	public Page<Employee> findAll(Pageable pageable);
	
	/**
	 * Finds employee by id.
	 * 
	 * @param id
	 *            The id of the wanted employee.
	 * @return The found employee. If no employee is found, this method returns
	 *         null.
	 */
	public Employee findById(Integer id) throws EntityNotFoundException;

	/**
	 * Updates the information of a employee.
	 * 
	 * @param updated
	 *            The information of the updated employee.
	 * @return The updated employee.
	 * @throws EntityNotFoundException
	 *             if no employee is found with given id.
	 */
	public Employee update(Employee updated) throws EntityNotFoundException;

	/**
	 * Retrieve the total count of the employees in the repository.
	 * 
	 * @param None
	 *            .
	 * @return The count of the employee.
	 */

	public long countAll();

}

