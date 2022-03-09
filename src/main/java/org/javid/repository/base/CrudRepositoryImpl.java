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
    protected final Class<T> tClass;

    @Override
    public EntityManager getManager() {
        if (manager == null || !manager.isOpen())
            manager = factory.createEntityManager();

        return manager;
    }

    @Override
    public List<T> findAll() {
        var jpql = String.format("FROM %s t", tClass.getSimpleName());
        return getManager().createQuery(jpql, tClass).getResultList();
    }

    @Override
    public T findById(ID id) {
        var em = getManager();
        var t = em.find(tClass, id);
        em.detach(t);
        return t;
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
        var jpql = String.format("DELETE FROM %s t WHERE t.id = :ID", tClass.getSimpleName());
        getManager().createQuery(jpql)
                .setParameter("ID", id)
                .executeUpdate();
    }
}
