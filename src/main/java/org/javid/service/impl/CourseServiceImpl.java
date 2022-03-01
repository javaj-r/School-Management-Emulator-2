package org.javid.service.impl;

import lombok.NonNull;
import org.javid.model.Course;
import org.javid.model.Student;
import org.javid.repository.CourseRepository;
import org.javid.service.CourseService;
import org.javid.service.base.BaseServiceImpl;

public class CourseServiceImpl extends BaseServiceImpl<Course, Integer, CourseRepository> implements CourseService {

    public CourseServiceImpl(@NonNull CourseRepository repository) {
        super(repository);
    }

    @Override
    public Integer save(Course entity) {
        return repository.save(entity);
    }

    @Override
    public void findAllCoursesByStudentId(Student student) {
        repository.findAllCoursesByStudentId(student);
    }
}
