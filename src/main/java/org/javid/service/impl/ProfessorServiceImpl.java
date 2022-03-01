package org.javid.service.impl;

import lombok.NonNull;
import org.javid.model.Course;
import org.javid.model.Professor;
import org.javid.repository.ProfessorRepository;
import org.javid.service.ProfessorService;
import org.javid.service.base.BaseServiceImpl;

public class ProfessorServiceImpl extends BaseServiceImpl<Professor, Integer, ProfessorRepository> implements ProfessorService {

    public ProfessorServiceImpl(@NonNull ProfessorRepository repository) {
        super(repository);
    }

    @Override
    public Professor findByUsername(Professor entity) {
        return repository.findByUsername(entity);
    }

    @Override
    public Professor findByUsernameAndPassword(Professor entity) {
        return repository.findByUsernameAndPassword(entity);
    }

    @Override
    public Integer save(Professor entity) {
        return repository.save(entity);
    }

    @Override
    public void saveProfessorCourse(Professor professor, Course course, int termNumber) {
        repository.saveProfessorCourse(professor, course, termNumber);
    }
}
