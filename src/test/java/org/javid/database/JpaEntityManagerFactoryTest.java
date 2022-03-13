package org.javid.database;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JpaEntityManagerFactoryTest {

    @Test
    void getInstance() {
        assertNotNull(JpaEntityManagerFactory.getInstance());
    }
}