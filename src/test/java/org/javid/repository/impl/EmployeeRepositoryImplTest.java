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
        repository = new EmployeeRepositoryImpl(factory, Employee.class);
        entity1 = new Employee()
                .setUsername("e1")
                .setPassword("123")
                .setFirstname("fName1")
                .setLastname("lName1")
                .setNationalCode(123L)
                .setSalary(10_000_000L);

        entity2 = new Employee()
                .setUsername("e2")
                .setPassword("234")
                .setFirstname("fName2")
                .setLastname("lName2")
                .setNationalCode(234L)
                .setSalary(20_000_000L);
    }

    @AfterEach
    void tearDown() {
        var em = repository.getManager();
        repository.transaction(em, () -> em.createQuery("DELETE FROM Employee ")
                        .executeUpdate());
    }

    @Override
    protected void update() {
        repository.save(entity1);
        var id = entity1.getId();
        entity1.setPassword(entity2.getPassword())
                .setFirstname(entity2.getFirstname())
                .setLastname(entity2.getLastname())
                .setNationalCode(entity2.getNationalCode())
                .setSalary(entity2.getSalary());

        repository.update(entity1);

        var actual = repository.findById(id);

        assertAll(
                () -> assertEquals(entity2.getPassword(), actual.getPassword()),
                () -> assertEquals(entity2.getFirstname(), actual.getFirstname()),
                () -> assertEquals(entity2.getLastname(), actual.getLastname()),
                () -> assertEquals(entity2.getNationalCode(), actual.getNationalCode()),
                () -> assertEquals(entity2.getSalary(), actual.getSalary())
        );
    }
}