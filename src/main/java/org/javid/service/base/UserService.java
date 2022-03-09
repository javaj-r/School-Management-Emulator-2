package org.javid.service.base;

import org.javid.model.base.User;

public interface UserService<T extends User> {

    T findByUsername(T entity);

    T findByUsernameAndPassword(T entity);
}
