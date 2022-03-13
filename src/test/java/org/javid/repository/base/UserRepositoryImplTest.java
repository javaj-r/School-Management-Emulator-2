package org.javid.repository.base;

import org.javid.model.base.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
public abstract class UserRepositoryImplTest<T extends User, R extends UserRepository<T> & CrudRepository<T, Integer>> extends CrudRepositoryImplTest<T, Integer, R> {

    /**
     * Not for executing from hear
     */
    @Test
    void findByUsername() {
        repository.transaction(repository.getManager(), ()->repository.save(entity1));

        var notSaved = repository.findByUsername(entity2);
        var saved = repository.findByUsername(entity1);

        assertNull(notSaved);
        assertNotNull(saved);
    }

    /**
     * Not for executing from hear
     */
    @Test
    void findByUsernameAndPassword() {
        repository.transaction(repository.getManager(), ()->repository.save(entity1));

        var notSaved = repository.findByUsername(entity2);
        var saved = repository.findByUsername(entity1);

        assertNull(notSaved);
        assertNotNull(saved);
    }
}