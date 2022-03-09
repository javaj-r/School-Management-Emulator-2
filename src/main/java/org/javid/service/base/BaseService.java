package org.javid.service.base;

import org.javid.model.base.BaseEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @author javid
 * Created on 1/31/2022
 */
public interface BaseService<T extends BaseEntity<ID>, ID extends Serializable> {

    List<T> findAll();

    T findById(ID id);

    void save(T entity);

    void update(T entity);

    void deleteById(ID id);

}
