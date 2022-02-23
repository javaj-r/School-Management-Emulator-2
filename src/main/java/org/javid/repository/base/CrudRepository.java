package org.javid.repository.base;

import org.javid.model.base.BaseEntity;

import java.util.List;

/**
 * @author javid
 * Created on 1/30/2022
 */
public interface CrudRepository<T  extends BaseEntity<ID>, ID> {

    List<T> findAll();

    T findById(ID id);

    ID save(T entity);

    void update(T entity);

    void deleteById(ID id);

}
