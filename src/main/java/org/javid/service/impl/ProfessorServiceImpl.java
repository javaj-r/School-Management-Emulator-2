package org.javid.service.impl;

import lombok.NonNull;
import org.javid.model.Professor;
import org.javid.repository.ProfessorRepository;
import org.javid.service.ProfessorService;
import org.javid.service.base.UserServiceImpl;

public class ProfessorServiceImpl extends UserServiceImpl<Professor, ProfessorRepository> implements ProfessorService {

    public ProfessorServiceImpl(@NonNull ProfessorRepository repository) {
        super(repository);
    }
}
