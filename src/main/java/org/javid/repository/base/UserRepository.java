package org.javid.repository.base;

import org.javid.model.base.User;

public interface UserRepository<T extends User> {

    T findByUsername(T entity);

    T findByUsernameAndPassword(T entity);
}
