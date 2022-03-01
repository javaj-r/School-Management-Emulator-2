package org.javid.repository.impl;

import lombok.NonNull;
import org.javid.connection.PostgresConnection;
import org.javid.model.Course;
import org.javid.model.Professor;
import org.javid.repository.ProfessorRepository;
import org.javid.repository.base.CrudRepositoryImpl;
import org.javid.repository.base.PersonMapper;
import org.javid.repository.base.PrimitiveHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProfessorRepositoryImpl extends CrudRepositoryImpl<Professor, Integer> implements ProfessorRepository {

    private static final String SELECT_QUERY = "SELECT id, username, password, firstname, lastname, nationalCode, salary, facultyMember"
            + "\n FROM professor"
            + "\n WHERE 1 = 1"
            + "\n %s \n;";

    public ProfessorRepositoryImpl(PostgresConnection postgresConnection) {
        super(postgresConnection);
    }

    @Override
    protected Professor parseEntity(ResultSet resultSet) throws SQLException {
        long temp = resultSet.getLong("nationalCode");
        Long nationalCode = resultSet.wasNull() ? null : temp;
        temp = resultSet.getLong("salary");
        Long salary = resultSet.wasNull() ? null : temp;

        return new Professor()
                .setId(resultSet.getInt("id"))
                .setUsername(resultSet.getString("username"))
                .setPassword(resultSet.getString("password"))
                .setFirstname(resultSet.getString("firstname"))
                .setLastname(resultSet.getString("lastname"))
                .setNationalCode(nationalCode)
                .setSalary(salary)
                .setFacultyMember(resultSet.getBoolean("facultyMember"));
    }

    @Override
    protected void mapEntity(@NonNull PreparedStatement statement, @NonNull Professor entity) throws SQLException {
        PersonMapper.map(statement, entity);
        PrimitiveHandler.setLong(statement, 6, () -> entity.getSalary() == null, entity::getSalary);
        statement.setBoolean(7, entity.isFacultyMember());
    }

    @Override
    public List<Professor> findAll() {
        List<Professor> professors = new ArrayList<>();
        String query = String.format(SELECT_QUERY, "ORDER BY id");
        try (PreparedStatement statement = getPreparedStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                professors.add(parseEntity(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return professors;
    }

    @Override
    public Professor findById(Integer id) {
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
    public Integer save(Professor entity) {
        String query = "INSERT INTO professor(username, password, firstname, lastname, nationalCode, salary, facultyMember)"
                + "\n SELECT ?, ?, ?, ?, ?, ?, ?"
                + "\n WHERE NOT exists(SELECT 1 FROM professor WHERE username = ?);";
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
    public void update(Professor entity) {
        String query = "UPDATE professor"
                + "\n SET username      =?,"
                + "\n     password      =?,"
                + "\n     firstname     =?,"
                + "\n     lastname      =?,"
                + "\n     nationalCode  =?,"
                + "\n     salary        =?,"
                + "\n     facultyMember = ?"
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
        String query = "DELETE FROM professor"
                + "\n WHERE id = ?;";
        try (PreparedStatement statement = getPreparedStatement(query)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Professor findByUsername(Professor entity) {
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
    public Professor findByUsernameAndPassword(Professor entity) {
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

    @Override
    public void saveProfessorCourse(Professor professor, Course course, int termNumber) {
        String query = "INSERT INTO professor_course(professorId, courseId, termNumber)"
                + "\n VALUES (?, ?, ?);";
        try (PreparedStatement statement = getPreparedStatement(query)) {
            statement.setInt(1, professor.getId());
            statement.setInt(2, course.getId());
            statement.setInt(3, termNumber);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
