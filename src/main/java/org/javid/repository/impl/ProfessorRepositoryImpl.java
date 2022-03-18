package org.javid.repository.impl;

import org.javid.model.Professor;
import org.javid.repository.ProfessorRepository;
import org.javid.repository.base.UserRepositoryImpl;

import javax.persistence.EntityManagerFactory;

public class ProfessorRepositoryImpl extends UserRepositoryImpl<Professor> implements ProfessorRepository {

    public ProfessorRepositoryImpl(EntityManagerFactory factory, Class<Professor> professorClass) {
        super(factory, professorClass);
    }

    @Override
    public Class<Professor> getEntityClass() {
        return Professor.class;
    }
}
