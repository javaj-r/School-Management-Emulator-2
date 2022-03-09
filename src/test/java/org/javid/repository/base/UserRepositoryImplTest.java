package org.javid.repository.base;

import org.javid.model.base.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public abstract class UserRepositoryImplTest<T extends User, R extends UserRepository<T> & CrudRepository<T, Integer>> extends CrudRepositoryImplTest<T, Integer, R> {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findByUsername() {
    }

    @Test
    void findByUsernameAndPassword() {
    }
}