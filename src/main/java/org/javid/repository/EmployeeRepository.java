package org.javid.repository;

import org.javid.model.Employee;
import org.javid.repository.base.CrudRepository;
import org.javid.repository.base.UserRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Integer>, UserRepository<Employee> {
}
