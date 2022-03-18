package org.javid.repository.impl;

import org.javid.model.Employee;
import org.javid.repository.EmployeeRepository;
import org.javid.repository.base.UserRepositoryImpl;

import javax.persistence.EntityManagerFactory;

public class EmployeeRepositoryImpl extends UserRepositoryImpl<Employee> implements EmployeeRepository {

    public EmployeeRepositoryImpl(EntityManagerFactory factory, Class<Employee> employeeClass) {
        super(factory, employeeClass);
    }

    @Override
    public Class<Employee> getEntityClass() {
        return Employee.class;
    }
}
