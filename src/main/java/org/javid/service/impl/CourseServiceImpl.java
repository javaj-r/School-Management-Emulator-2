package org.javid.service.impl;

import lombok.NonNull;
import org.javid.model.Course;
import org.javid.repository.CourseRepository;
import org.javid.service.CourseService;
import org.javid.service.base.BaseServiceImpl;

public class CourseServiceImpl extends BaseServiceImpl<Course, Integer, CourseRepository> implements CourseService {

    public CourseServiceImpl(@NonNull CourseRepository repository) {
        super(repository);
    }
}
