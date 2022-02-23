package org.javid.repository.impl;

import lombok.NonNull;
import org.javid.connection.PostgresConnection;
import org.javid.model.Student;
import org.javid.repository.StudentRepository;
import org.javid.repository.base.CrudRepositoryImpl;
import org.javid.repository.base.PersonMapper;
import org.javid.repository.base.PrimitiveHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StudentRepositoryImpl extends CrudRepositoryImpl<Student, Integer> implements StudentRepository {

    private static final String SELECT_QUERY = "SELECT id, username, password, firstname, lastname, nationalCode, studentCode, termNumber"
            + "\n FROM student"
            + "\n WHERE 1 = 1"
            + "\n %s \n;";

    public StudentRepositoryImpl(PostgresConnection postgresConnection) {
        super(postgresConnection);
    }

    @Override
    protected Student parseEntity(ResultSet resultSet) throws SQLException {
        long temp = resultSet.getLong("nationalCode");
        Long nationalCode = resultSet.wasNull() ? null : temp;
        int intTemp = resultSet.getInt("studentCode");
        Integer studentCode = resultSet.wasNull() ? null : intTemp;

        return new Student()
                .setId(resultSet.getInt("id"))
                .setUsername(resultSet.getString("username"))
                .setPassword(resultSet.getString("password"))
                .setFirstname(resultSet.getString("firstname"))
                .setLastname(resultSet.getString("lastname"))
                .setNationalCode(nationalCode)
                .setStudentCode(studentCode)
                .setTermNumber(resultSet.getInt("termNumber"));
    }

    @Override
    protected void mapEntity(@NonNull PreparedStatement statement, @NonNull Student entity) throws SQLException {
        PersonMapper.map(statement, entity);
        PrimitiveHandler.setInt(statement, 6, () -> entity.getStudentCode() == null, entity::getStudentCode);
        statement.setInt(7, entity.getTermNumber());
    }

    @Override
    public List<Student> findAll() {
        List<Student> students = new ArrayList<>();
        String query = String.format(SELECT_QUERY, "ORDER BY id");
        try (PreparedStatement statement = getPreparedStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                students.add(parseEntity(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    @Override
    public Student findById(Integer id) {
        String query = String.format(SELECT_QUERY, "AND id = ?");
        try (PreparedStatement statement = getPreparedStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return parseEntity(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer save(Student entity) {
        String query = "INSERT INTO student(username, password, firstname, lastname, nationalCode, studentCode, termNumber)"
                + "\n SELECT ?, ?, ?, ?, ?, ?, ?"
                + "\n WHERE NOT exists(SELECT 1 FROM student WHERE username = ?);";
        try (PreparedStatement statement = getPreparedStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            mapEntity(statement, entity);
            statement.setString(8, entity.getUsername());
            statement.execute();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next())
                return resultSet.getInt(ID);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Student entity) {
        String query = "UPDATE student"
                + "\n SET username     =?,"
                + "\n     password     =?,"
                + "\n     firstname    =?,"
                + "\n     lastname     =?,"
                + "\n     nationalCode =?,"
                + "\n     studentCode  =?,"
                + "\n     termNumber   = ?"
                + "\n WHERE id = ?;";
        try (PreparedStatement statement = getPreparedStatement(query)) {
            mapEntity(statement, entity);
            PrimitiveHandler.setInt(statement, 8, entity::isNew, entity::getId);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Integer id) {
        String query = "DELETE FROM student"
                + "\n WHERE id = ?;";
        try (PreparedStatement statement = getPreparedStatement(query)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Student findByUsername(Student entity) {
        String query = String.format(SELECT_QUERY, "AND username = ?");
        try (PreparedStatement statement = getPreparedStatement(query)) {
            statement.setString(1, entity.getUsername());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return parseEntity(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Student findByUsernameAndPassword(Student entity) {
        String query = String.format(SELECT_QUERY, "AND username = ?"
                + "\n AND password = ?");
        try (PreparedStatement statement = getPreparedStatement(query)) {
            statement.setString(1, entity.getUsername());
            statement.setString(2, entity.getPassword());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
                return parseEntity(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
