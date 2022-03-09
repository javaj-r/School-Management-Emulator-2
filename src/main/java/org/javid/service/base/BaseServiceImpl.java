package org.javid.service.base;

import lombok.RequiredArgsConstructor;
import org.javid.model.base.BaseEntity;
import org.javid.repository.base.CrudRepository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public abstract class BaseServiceImpl<T extends BaseEntity<ID>, ID extends Serializable, R extends CrudRepository<T, ID>>
        implements BaseService<T, ID> {

    protected final R repository;

    @Override
    public List<T> findAll() {
        try {
            return repository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public T findById(ID id) {
        return repository.findById(id);
    }

    @Override
    public void save(T entity) {
        repository.transaction(repository.getManager(), () -> repository.save(entity));
    }

    @Override
    public void deleteById(ID id) {
        repository.transaction(repository.getManager(), () -> repository.deleteById(id));
    }

    @Override
    public void update(T entity) {
        repository.transaction(repository.getManager(), () -> repository.update(entity));
    }
}
