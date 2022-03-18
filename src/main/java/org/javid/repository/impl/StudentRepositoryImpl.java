package org.javid.repository.impl;

import org.javid.model.Student;
import org.javid.repository.StudentRepository;
import org.javid.repository.base.UserRepositoryImpl;

import javax.persistence.EntityManagerFactory;

public class StudentRepositoryImpl extends UserRepositoryImpl<Student> implements StudentRepository {

    public StudentRepositoryImpl(EntityManagerFactory factory, Class<Student> studentClass) {
        super(factory, studentClass);
    }

    @Override
    public Class<Student> getEntityClass() {
        return Student.class;
    }
}
