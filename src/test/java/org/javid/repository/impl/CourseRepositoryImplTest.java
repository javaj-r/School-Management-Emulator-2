package org.javid.repository.impl;

import org.javid.model.Course;
import org.javid.model.Employee;
import org.javid.repository.CourseRepository;
import org.javid.repository.base.CrudRepositoryImplTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CourseRepositoryImplTest extends CrudRepositoryImplTest<Course, Integer, CourseRepository> {

    @BeforeEach
    void setUp() {
        repository = new CourseRepositoryImpl(factory, Course.class);
        entity1 = new Course()
                .setName("Math-1")
                .setUnit(2);
        entity2 = new Course()
                .setName("Physic-2")
                .setUnit(3);
    }

    @AfterEach
    void tearDown() {
        var em = repository.getManager();
        repository.transaction(em, () -> em.createQuery("DELETE FROM Course ")
                .executeUpdate());
    }

    @Override
    protected void update() {
        repository.save(entity1);
        var id = entity1.getId();
        entity1.setName(entity2.getName())
                .setUnit(entity2.getUnit());

        repository.update(entity1);

        var actual = repository.findById(id);

        assertAll(
                () -> assertEquals(entity2.getName(), actual.getName()),
                () -> assertEquals(entity2.getUnit(), actual.getUnit())
        );
    }
}