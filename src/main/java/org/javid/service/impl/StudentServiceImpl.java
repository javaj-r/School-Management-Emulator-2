package org.javid.service.impl;

import lombok.NonNull;
import org.javid.model.Course;
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

    @Override
    public void saveStudentCourse(Student student, Course course) {
        repository.saveStudentCourse(student, course);
    }

    @Override
    public void updateStudentCourseScore(Student student, Course course, int score) {
        repository.updateStudentCourseScore(student, course, score);
    }
}
