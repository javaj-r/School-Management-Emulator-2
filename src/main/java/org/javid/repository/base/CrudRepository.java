package org.javid.repository.base;

import org.javid.model.base.BaseEntity;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

/**
 * @author javid
 * Created on 1/30/2022
 */
public interface CrudRepository<T extends BaseEntity<ID>, ID extends Serializable> {

    EntityManager getManager();

    default void transaction(EntityManager manager, Runnable runnable) {
        var t = manager.getTransaction();
        try {
            t.begin();
            runnable.run();
            t.commit();
        } catch (Exception e) {
            t.rollback();
            e.printStackTrace();
        }
    }

    List<T> findAll();

    T findById(ID id);

    void save(T entity);

    void update(T entity);

    void deleteById(ID id);

}
