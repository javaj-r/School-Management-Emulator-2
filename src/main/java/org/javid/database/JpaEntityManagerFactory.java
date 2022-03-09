package org.javid.database;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author javid
 * Created on 3/9/2022
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JpaEntityManagerFactory {

    private static class LazyHolder {
        private static final String PERSISTENCE_UNIT = "persistence-unit";
        private static final EntityManagerFactory INSTANCE = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
    }

    public static EntityManagerFactory getInstance() {
        return LazyHolder.INSTANCE;
    }
}
