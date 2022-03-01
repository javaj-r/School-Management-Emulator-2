package org.javid.repository;

import org.javid.model.Course;
import org.javid.model.Student;
import org.javid.repository.base.CrudRepository;
import org.javid.repository.base.UserRepository;

public interface StudentRepository extends CrudRepository<Student, Integer>, UserRepository<Student> {

    void saveStudentCourse(Student student, Course course);

    void updateStudentCourseScore(Student student, Course course, int score);
}
