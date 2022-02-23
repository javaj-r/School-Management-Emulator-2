package org.javid.service;

import org.javid.model.Employee;
import org.javid.service.base.BaseService;
import org.javid.service.base.UserService;

public interface EmployeeService extends BaseService<Employee, Integer>, UserService<Employee> {
}
