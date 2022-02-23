package org.javid.service.base;

import lombok.RequiredArgsConstructor;
import org.javid.model.Course;
import org.javid.model.base.BaseEntity;
import org.javid.repository.base.CrudRepository;

import java.util.List;

@RequiredArgsConstructor
public abstract class BaseServiceImpl<T extends BaseEntity<ID>, ID, R extends CrudRepository<T, ID>> implements BaseService<T, ID> {

    protected final R repository;

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(ID id) {
        repository.deleteById(id);
    }

    @Override
    public void update(T entity) {
        repository.update(entity);
    }
}
