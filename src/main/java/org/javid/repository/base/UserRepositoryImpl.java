package org.javid.repository.base;

import org.javid.model.base.User;

import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;

/**
 * @author javid
 * Created on 3/9/2022
 */
public abstract class UserRepositoryImpl<T extends User> extends CrudRepositoryImpl<T, Integer> implements UserRepository<T> {

    public UserRepositoryImpl(EntityManagerFactory factory, Class<T> tClass) {
        super(factory, tClass);
    }

    @Override
    public T findByUsername(T entity) {
        try {
            var jpql = String.format("FROM %s t WHERE t.username = :Username"
                    , tClass.getSimpleName());
            return getManager().createQuery(jpql, tClass)
                    .setParameter("Username", entity.getUsername())
                    .getSingleResult();
        } catch (NoResultException ignored) {
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public T findByUsernameAndPassword(T entity) {
        try {
            var jpql = String.format(
                    "FROM %s t WHERE t.username = :Username AND t.password = :Password"
                    , tClass.getSimpleName());
            return getManager().createQuery(jpql, tClass)
                    .setParameter("Username", entity.getUsername())
                    .setParameter("Password", entity.getPassword())
                    .getSingleResult();
        } catch (NoResultException ignored) {
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
