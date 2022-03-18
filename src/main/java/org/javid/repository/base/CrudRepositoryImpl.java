package org.javid.repository.base;

import lombok.RequiredArgsConstructor;
import org.javid.model.base.BaseEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.Serializable;
import java.util.List;

@RequiredArgsConstructor
public abstract class CrudRepositoryImpl<T extends BaseEntity<ID>, ID extends Serializable>
        implements CrudRepository<T, ID> {

    private final EntityManagerFactory factory;
    private EntityManager manager;

    @Override
    public EntityManager getManager() {
        if (manager == null || !manager.isOpen())
            manager = factory.createEntityManager();

        return manager;
    }

    @Override
    public List<T> findAll() {
        var jpql = String.format("FROM %s t", getEntityClass().getSimpleName());
        return getManager().createQuery(jpql, getEntityClass()).getResultList();
    }

    @Override
    public T findById(ID id) {
        var em = getManager();
        return em.find(getEntityClass(), id);
    }

    @Override
    public void save(T entity) {
        getManager().persist(entity);
    }

    @Override
    public void update(T entity) {
        getManager().merge(entity);
    }

    @Override
    public void deleteById(ID id) {
        var jpql = String.format("DELETE FROM %s t WHERE t.id = :ID", getEntityClass().getSimpleName());
        getManager().createQuery(jpql)
                .setParameter("ID", id)
                .executeUpdate();
    }
}
