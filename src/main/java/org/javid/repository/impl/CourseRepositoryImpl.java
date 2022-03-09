package org.javid.repository.impl;

import org.javid.model.Course;
import org.javid.repository.CourseRepository;
import org.javid.repository.base.CrudRepositoryImpl;

import javax.persistence.EntityManagerFactory;

public class CourseRepositoryImpl extends CrudRepositoryImpl<Course, Integer> implements CourseRepository {

    public CourseRepositoryImpl(EntityManagerFactory factory, Class<Course> courseClass) {
        super(factory, courseClass);
    }
}
