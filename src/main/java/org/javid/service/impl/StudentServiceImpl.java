package org.javid.service.impl;

import lombok.NonNull;
import org.javid.model.Student;
import org.javid.repository.StudentRepository;
import org.javid.service.StudentService;
import org.javid.service.base.BaseServiceImpl;

public class StudentServiceImpl extends BaseServiceImpl<Student, Integer, StudentRepository> implements StudentService {

    public StudentServiceImpl(@NonNull StudentRepository repository) {
        super(repository);
    }

    @Override
    public Student findByUsername(Student entity) {
        return repository.findByUsername(entity);
    }

    @Override
    public Student findByUsernameAndPassword(Student entity) {
        return repository.findByUsernameAndPassword(entity);
    }

    @Override
    public Integer save(Student entity) {
        return repository.save(entity);
    }
}
