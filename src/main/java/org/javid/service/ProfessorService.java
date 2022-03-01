package org.javid.service;

import org.javid.model.Course;
import org.javid.model.Professor;
import org.javid.service.base.BaseService;
import org.javid.service.base.UserService;

public interface ProfessorService extends BaseService<Professor, Integer>, UserService<Professor> {

    void saveProfessorCourse(Professor professor, Course course, int termNumber);
}
