package org.javid.context;


import org.javid.connection.PostgresConnection;
import org.javid.console.EmployeeConsole;
import org.javid.console.ProfessorConsole;
import org.javid.repository.CourseRepository;
import org.javid.repository.EmployeeRepository;
import org.javid.repository.ProfessorRepository;
import org.javid.repository.StudentRepository;
import org.javid.repository.impl.CourseRepositoryImpl;
import org.javid.repository.impl.EmployeeRepositoryImpl;
import org.javid.repository.impl.ProfessorRepositoryImpl;
import org.javid.repository.impl.StudentRepositoryImpl;
import org.javid.service.CourseService;
import org.javid.service.EmployeeService;
import org.javid.service.ProfessorService;
import org.javid.service.StudentService;
import org.javid.service.impl.CourseServiceImpl;
import org.javid.service.impl.EmployeeServiceImpl;
import org.javid.service.impl.ProfessorServiceImpl;
import org.javid.service.impl.StudentServiceImpl;

public class ApplicationContext {

    private static PostgresConnection getPostgresConnection() {
        return PostgresConnection.getInstance();
    }

    public static CourseService getCourseService() {
        return new CourseServiceImpl(getCourseRepository());
    }

    private static CourseRepository getCourseRepository() {
        return new CourseRepositoryImpl(getPostgresConnection());
    }

    public static EmployeeService getEmployeeService() {
        return new EmployeeServiceImpl(getEmployeeRepository());
    }

    public static EmployeeRepository getEmployeeRepository() {
        return new EmployeeRepositoryImpl(getPostgresConnection());
    }

    public static ProfessorService getProfessorService() {
        return new ProfessorServiceImpl(getProfessorRepository());
    }

    private static ProfessorRepository getProfessorRepository() {
        return new ProfessorRepositoryImpl(getPostgresConnection());
    }

    public static StudentService getStudentService() {
        return new StudentServiceImpl(getStudentRepository());

    }

    private static StudentRepository getStudentRepository() {
        return new StudentRepositoryImpl(getPostgresConnection());
    }

    public static EmployeeConsole getEmployeeConsole() {
        return new EmployeeConsole(getEmployeeService());
    }

    public static ProfessorConsole getProfessorConsole() {
        return new ProfessorConsole(getProfessorService());
    }
}