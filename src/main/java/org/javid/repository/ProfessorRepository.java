package org.javid.repository;

import org.javid.model.Course;
import org.javid.model.Professor;
import org.javid.repository.base.CrudRepository;
import org.javid.repository.base.UserRepository;

public interface ProfessorRepository extends CrudRepository<Professor, Integer>, UserRepository<Professor> {

    void saveProfessorCourse(Professor professor, Course course, int termNumber);
}
