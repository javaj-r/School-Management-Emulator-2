package org.javid.repository.impl;

import org.javid.model.Professor;
import org.javid.model.Student;
import org.javid.repository.StudentRepository;
import org.javid.repository.base.UserRepositoryImplTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class StudentRepositoryImplTest extends UserRepositoryImplTest<Student, StudentRepository> {

    @BeforeEach
    void setUp() {
        repository = new StudentRepositoryImpl(factory, Student.class);
        entity1 = new Student()
                .setUsername("p1")
                .setPassword("123")
                .setFirstname("fName1")
                .setLastname("lName1")
                .setNationalCode(123L)
                .setTermNumber(1);

        entity2 = new Student()
                .setUsername("e2")
                .setPassword("234")
                .setFirstname("fName2")
                .setLastname("lName2")
                .setNationalCode(234L)
                .setTermNumber(2);
    }

    @AfterEach
    void tearDown() {
        var em = repository.getManager();
        repository.transaction(em, () -> em.createQuery("DELETE FROM Student ")
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
                .setTermNumber(entity2.getTermNumber());

        repository.update(entity1);

        var actual = repository.findById(id);

        assertAll(
                () -> assertEquals(entity2.getPassword(), actual.getPassword()),
                () -> assertEquals(entity2.getFirstname(), actual.getFirstname()),
                () -> assertEquals(entity2.getLastname(), actual.getLastname()),
                () -> assertEquals(entity2.getNationalCode(), actual.getNationalCode()),
                () -> assertEquals(entity2.getTermNumber(), actual.getTermNumber())
        );
    }
}