package org.javid.service.impl;

import lombok.NonNull;
import org.javid.model.Employee;
import org.javid.repository.EmployeeRepository;
import org.javid.service.EmployeeService;
import org.javid.service.base.BaseServiceImpl;

public class EmployeeServiceImpl extends BaseServiceImpl<Employee, Integer, EmployeeRepository> implements EmployeeService {

    public EmployeeServiceImpl(@NonNull EmployeeRepository repository) {
        super(repository);
    }

    @Override
    public Employee findByUsername(Employee entity) {
        return null;
    }

    @Override
    public Employee findByUsernameAndPassword(Employee entity) {
        return repository.findByUsernameAndPassword(entity);
    }

    @Override
    public Integer save(Employee entity) {
        return null;
    }
}
