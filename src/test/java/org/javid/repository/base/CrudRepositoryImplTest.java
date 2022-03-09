package org.javid.repository.base;

import org.javid.database.JpaEntityManagerFactory;
import org.javid.model.base.BaseEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import java.io.Serializable;

import static org.junit.jupiter.api.Assertions.*;

public abstract class CrudRepositoryImplTest<T extends BaseEntity<ID>, ID extends Serializable, R extends CrudRepository<T, ID> > {

    protected EntityManagerFactory factory = JpaEntityManagerFactory.getInstance();
    protected R repository;
    protected T entity1;
    protected T entity2;

    @Test
    void getManager() {

    }

    @Test
    void findAll() {
    }

    @Test
    void findById() {
    }

    @Test
    void save() {
    }

    @Test
    void update() {
    }

    @Test
    void deleteById() {
    }
}