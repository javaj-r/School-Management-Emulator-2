package org.javid.service.impl;

import lombok.NonNull;
import org.javid.model.Employee;
import org.javid.repository.EmployeeRepository;
import org.javid.service.EmployeeService;
import org.javid.service.base.UserServiceImpl;

public class EmployeeServiceImpl extends UserServiceImpl<Employee, EmployeeRepository> implements EmployeeService {

    public EmployeeServiceImpl(@NonNull EmployeeRepository repository) {
        super(repository);
    }
}
