package org.javid.repository.impl;

import lombok.NonNull;
import org.javid.connection.PostgresConnection;
import org.javid.model.Employee;
import org.javid.repository.EmployeeRepository;
import org.javid.repository.base.CrudRepositoryImpl;
import org.javid.repository.base.PersonMapper;
import org.javid.repository.base.PrimitiveHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepositoryImpl extends CrudRepositoryImpl<Employee, Integer> implements EmployeeRepository {

    private static final String SELECT_QUERY = "SELECT id, username, password, firstname, lastname, nationalCode, salary"
            + "\n FROM employee"
            + "\n WHERE 1 = 1"
            + "\n %s ;";

    public EmployeeRepositoryImpl(PostgresConnection postgresConnection) {
        super(postgresConnection);
    }

    @Override
    protected Employee parseEntity(ResultSet resultSet) throws SQLException {
        long temp = resultSet.getLong("nationalCode");
        Long nationalCode = resultSet.wasNull() ? null : temp;
        temp = resultSet.getLong("salary");
        Long salary = resultSet.wasNull() ? null : temp;

        return new Employee()
                .setId(resultSet.getInt("id"))
                .setUsername(resultSet.getString("username"))
                .setPassword(resultSet.getString("password"))
                .setFirstname(resultSet.getString("firstname"))
                .setLastname(resultSet.getString("lastname"))
                .setNationalCode(nationalCode)
                .setSalary(salary);
    }

    @Override
    protected void mapEntity(@NonNull PreparedStatement statement, @NonNull Employee entity) throws SQLException {
        PersonMapper.map(statement, entity);
        PrimitiveHandler.setLong(statement, 6, () -> entity.getSalary() == null, entity::getSalary);
    }

    @Override
    public List<Employee> findAll() {
        List<Employee> employees = new ArrayList<>();
        String query = String.format(SELECT_QUERY, "ORDER BY id");
        try (PreparedStatement statement = getPreparedStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                employees.add(parseEntity(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    @Override
    public Employee findById(Integer id) {
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
    public Integer save(Employee entity) {
        String query = "INSERT INTO employee(username, password, firstname, lastname, nationalCode, salary)"
                + "\n SELECT ?, ?, ?, ?, ?, ?"
                + "\n WHERE NOT exists(SELECT 1 FROM employee WHERE username = ?);";
        try (PreparedStatement statement = getPreparedStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            mapEntity(statement, entity);
            statement.setString(7, entity.getUsername());
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
    public void update(Employee entity) {
        String query = "UPDATE employee"
                + "\n SET username     =?,"
                + "\n     password     =?,"
                + "\n     firstname    =?,"
                + "\n     lastname     =?,"
                + "\n     nationalCode =?,"
                + "\n     salary       =?"
                + "\n WHERE id = ?;";
        try (PreparedStatement statement = getPreparedStatement(query)) {
            mapEntity(statement, entity);
            PrimitiveHandler.setInt(statement, 7, entity::isNew, entity::getId);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Integer id) {
        String query = "DELETE FROM employee"
                + "\n WHERE id = ?;";
        try (PreparedStatement statement = getPreparedStatement(query)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Employee findByUsername(Employee entity) {
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
    public Employee findByUsernameAndPassword(Employee entity) {
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
