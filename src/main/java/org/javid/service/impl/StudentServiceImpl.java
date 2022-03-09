package org.javid.service.impl;

import lombok.NonNull;
import org.javid.model.Student;
import org.javid.repository.StudentRepository;
import org.javid.service.StudentService;
import org.javid.service.base.UserServiceImpl;

public class StudentServiceImpl extends UserServiceImpl<Student, StudentRepository> implements StudentService {

    public StudentServiceImpl(@NonNull StudentRepository repository) {
        super(repository);
    }
}
