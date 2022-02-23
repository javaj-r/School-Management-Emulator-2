package org.javid.repository.impl;

import lombok.NonNull;
import org.javid.connection.PostgresConnection;
import org.javid.model.Course;
import org.javid.model.Student;
import org.javid.repository.CourseRepository;
import org.javid.repository.base.CrudRepositoryImpl;
import org.javid.repository.base.PrimitiveHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class CourseRepositoryImpl extends CrudRepositoryImpl<Course, Integer> implements CourseRepository {

    private static final String SELECT_QUERY = "SELECT id, name, unit, requiredCourseId"
            + "\n FROM course"
            + "\n WHERE 1 = 1"
            + "\n %s \n;";

    public CourseRepositoryImpl(PostgresConnection postgresConnection) {
        super(postgresConnection);
    }

    @Override
    protected Course parseEntity(ResultSet resultSet) throws SQLException {
        int temp = resultSet.getInt("unit");
        Integer unit = resultSet.wasNull() ? null : temp;
        temp = resultSet.getInt("requiredCourseId");
        Integer requiredCourseId = resultSet.wasNull() ? null : temp;

        return new Course()
                .setName(resultSet.getString("name"))
                .setUnit(unit)
                .setRequiredCourse(new Course().setId(requiredCourseId));
    }

    @Override
    protected void mapEntity(@NonNull PreparedStatement statement, @NonNull Course entity) throws SQLException {
        statement.setString(1, entity.getName());
        PrimitiveHandler.setInt(statement, 2, () -> entity.getUnit() == null, entity::getUnit);
        PrimitiveHandler.setInt(statement, 3
                , () -> entity.getRequiredCourse() == null || entity.getRequiredCourse().isNew()
                , entity.getRequiredCourse()::getId);
    }

    @Override
    public List<Course> findAll() {
        List<Course> courses = new ArrayList<>();
        String query = String.format(SELECT_QUERY, "ORDER BY id");
        try (PreparedStatement statement = getPreparedStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                courses.add(parseEntity(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    @Override
    public Course findById(Integer id) {
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
    public Integer save(Course entity) {
        String query = "INSERT INTO course(name, unit, requiredCourseId)"
                + "\n VALUES (?, ?, ?);";
        try (PreparedStatement statement = getPreparedStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            mapEntity(statement, entity);
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
    public void update(Course entity) {
        String query = "UPDATE course" +
                "\n SET name = ?," +
                "\n unit = ?," +
                "\n requiredCourseId = ?" +
                "\n WHERE id = ?;";
        try (PreparedStatement statement = getPreparedStatement(query)){
            mapEntity(statement, entity);
            PrimitiveHandler.setInt(statement, 4, entity::isNew, entity::getId);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Integer id) {
        String query = "DELETE FROM course"
                + "\n WHERE id = ?;";
        try (PreparedStatement statement = getPreparedStatement(query)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
