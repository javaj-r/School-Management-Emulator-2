package org.javid.repository.base;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.javid.model.base.Person;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PersonMapper {

    public static void map(@NonNull PreparedStatement statement, @NonNull Person entity) throws SQLException {
        statement.setString(1, entity.getUsername());
        statement.setString(2, entity.getPassword());
        statement.setString(3, entity.getFirstname());
        statement.setString(4, entity.getLastname());
        PrimitiveHandler.setLong(statement, 5, () -> entity.getNationalCode() == null, entity::getNationalCode);
    }
}
