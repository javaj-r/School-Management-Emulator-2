package org.javid.repository.base;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.function.BooleanSupplier;

/**
 * @author javid
 * Created on 1/23/2022
 */
@FunctionalInterface
public interface PrimitiveHandler<I> {

    static void setLong(PreparedStatement statement, int index, BooleanSupplier isNull, PrimitiveHandler<Long> handler) throws SQLException {
        if (isNull.getAsBoolean()) {
            setNull(statement, index, Types.BIGINT);
        } else {
            statement.setLong(index, handler.get());
        }
    }

    static void setInt(PreparedStatement statement, int index, BooleanSupplier isNull, PrimitiveHandler<Integer> handler) throws SQLException {
        if (isNull.getAsBoolean()) {
            setNull(statement, index, Types.INTEGER);
        } else {
            statement.setInt(index, handler.get());
        }
    }

    static void setNull(PreparedStatement statement, int index, int sqlType) throws SQLException {
        statement.setNull(index, sqlType);
    }

    I get();
}
