package org.javid.repository.base;

import org.javid.database.JpaEntityManagerFactory;
import org.javid.model.base.BaseEntity;
import org.junit.jupiter.api.*;

import javax.persistence.EntityManagerFactory;
import java.io.Serializable;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
public abstract class CrudRepositoryImplTest<T extends BaseEntity<ID>, ID extends Serializable, R extends CrudRepository<T, ID> > {

    protected EntityManagerFactory factory = JpaEntityManagerFactory.getInstance();
    protected R repository;
    protected T entity1;
    protected T entity2;

    /**
     * Not for executing from hear
     */
    @Test
    void getManager() {
        var entityManager = repository.getManager();
        var entityManager1 = repository.getManager();

        assertNotNull(entityManager);
        assertTrue(entityManager.isOpen());
        entityManager.close();
        assertFalse(entityManager1.isOpen());

        var entityManager2 = repository.getManager();
        assertFalse(entityManager.isOpen());
        assertTrue(entityManager2.isOpen());
    }

    /**
     * Not for executing from hear
     */
    @Test
    void findAll() {
        repository.transaction(repository.getManager(), ()->repository.save(entity1));
        repository.transaction(repository.getManager(), () -> repository.save(entity2));

        var list = repository.findAll();
        assertEquals(2, list.size());
    }

    /**
     * Not for executing from hear
     */
    @Test
    void findById() {
        repository.transaction(repository.getManager(), () -> repository.save(entity1));
        var id = entity1.getId();

        var actual = repository.findById(id);

        assertEquals(entity1, actual);
    }

    /**
     * Not for executing from hear
     */
    @Test
    void save() {
        repository.transaction(repository.getManager(), () -> repository.save(entity1));
        var id = entity1.getId();

        var actual = repository.findById(id);

        assertNotNull(actual);
        assertEquals(entity1, actual);
    }

    /**
     * Not for executing from hear
     */
    @Test
    protected abstract void update();

    /**
     * Not for executing from hear
     */
    @Test
    void deleteById() {
        repository.transaction(repository.getManager(), () -> repository.save(entity1));
        var id = entity1.getId();

        repository.transaction(repository.getManager(), () -> repository.deleteById(id));
        var actual = repository.findAll();

        assertEquals(0, actual.size());
    }
}