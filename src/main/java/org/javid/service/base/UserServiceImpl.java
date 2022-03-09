package org.javid.service.base;

import org.javid.model.base.User;
import org.javid.repository.base.CrudRepository;
import org.javid.repository.base.UserRepository;

public class UserServiceImpl<T extends User, R extends UserRepository<T> & CrudRepository<T, Integer>>
        extends BaseServiceImpl<T, Integer, R> implements UserService<T> {

    public UserServiceImpl(R repository) {
        super(repository);
    }

    @Override
    public T findByUsername(T entity) {
        return repository.findByUsername(entity);
    }

    @Override
    public T findByUsernameAndPassword(T entity) {
        return repository.findByUsernameAndPassword(entity);
    }
}
