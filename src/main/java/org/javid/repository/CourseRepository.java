package org.javid.repository;

import org.javid.model.Course;
import org.javid.model.Student;
import org.javid.repository.base.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Integer> {

    void findAllCoursesByStudentId(Student student);
}
