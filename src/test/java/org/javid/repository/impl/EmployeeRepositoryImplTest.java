package org.javid.repository.impl;

import org.javid.model.Employee;
import org.javid.repository.EmployeeRepository;
import org.javid.repository.base.UserRepositoryImplTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeRepositoryImplTest extends UserRepositoryImplTest<Employee, EmployeeRepository> {

    @BeforeEach
    void setUp() {
        System.out.println("setUp");
    }

    @AfterEach
    void tearDown() {
        System.out.println("tearDown");
    }
}