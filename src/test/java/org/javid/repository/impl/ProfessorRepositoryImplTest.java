package org.javid.repository.impl;

import org.javid.model.Professor;
import org.javid.repository.ProfessorRepository;
import org.javid.repository.base.UserRepositoryImplTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ProfessorRepositoryImplTest extends UserRepositoryImplTest<Professor, ProfessorRepository> {

    @BeforeEach
    void setUp() {
        repository = new ProfessorRepositoryImpl(factory, Professor.class);
        entity1 = new Professor()
                .setUsername("p1")
                .setPassword("123")
                .setFirstname("fName1")
                .setLastname("lName1")
                .setNationalCode(123L)
                .setFacultyMember(false)
                .setTermNumber(1);

        entity2 = new Professor()
                .setUsername("e2")
                .setPassword("234")
                .setFirstname("fName2")
                .setLastname("lName2")
                .setFacultyMember(true)
                .setTermNumber(2);
    }

    @AfterEach
    void tearDown() {
        var em = repository.getManager();
        repository.transaction(em, () -> em.createQuery("DELETE FROM Professor ")
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
                .setFacultyMember(entity2.isFacultyMember())
                .setTermNumber(entity2.getTermNumber());

        repository.update(entity1);

        var actual = repository.findById(id);

        assertAll(
                () -> assertEquals(entity2.getPassword(), actual.getPassword()),
                () -> assertEquals(entity2.getFirstname(), actual.getFirstname()),
                () -> assertEquals(entity2.getLastname(), actual.getLastname()),
                () -> assertEquals(entity2.getNationalCode(), actual.getNationalCode()),
                () -> assertEquals(entity2.isFacultyMember(), actual.isFacultyMember()),
                () -> assertEquals(entity2.getTermNumber(), actual.getTermNumber())
        );
    }
}