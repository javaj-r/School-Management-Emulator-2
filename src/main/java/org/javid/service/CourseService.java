package org.javid.service;

import org.javid.model.Course;
import org.javid.model.Student;
import org.javid.service.base.BaseService;

public interface CourseService extends BaseService<Course, Integer> {

    void findAllCoursesByStudentId(Student student);
}
