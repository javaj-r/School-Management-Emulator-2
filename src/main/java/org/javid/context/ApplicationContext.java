package org.javid.context;

import org.javid.console.CourseConsole;
import org.javid.console.EmployeeConsole;
import org.javid.console.ProfessorConsole;
import org.javid.console.StudentConsole;
import org.javid.database.JpaEntityManagerFactory;
import org.javid.model.Course;
import org.javid.model.Employee;
import org.javid.model.Professor;
import org.javid.model.Student;
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

import javax.persistence.EntityManagerFactory;

public class ApplicationContext {

    private static EntityManagerFactory getEntityManagerFactory() {
        return JpaEntityManagerFactory.getInstance();
    }

    public static CourseService getCourseService() {
        return new CourseServiceImpl(getCourseRepository());
    }

    private static CourseRepository getCourseRepository() {
        return new CourseRepositoryImpl(getEntityManagerFactory(), Course.class);
    }

    public static EmployeeService getEmployeeService() {
        return new EmployeeServiceImpl(getEmployeeRepository());
    }

    public static EmployeeRepository getEmployeeRepository() {
        return new EmployeeRepositoryImpl(getEntityManagerFactory(), Employee.class);
    }

    public static ProfessorService getProfessorService() {
        return new ProfessorServiceImpl(getProfessorRepository());
    }

    private static ProfessorRepository getProfessorRepository() {
        return new ProfessorRepositoryImpl(getEntityManagerFactory(), Professor.class);
    }

    public static StudentService getStudentService() {
        return new StudentServiceImpl(getStudentRepository());
    }

    private static StudentRepository getStudentRepository() {
        return new StudentRepositoryImpl(getEntityManagerFactory(), Student.class);
    }

    public static EmployeeConsole getEmployeeConsole() {
        return new EmployeeConsole(getEmployeeService());
    }

    public static ProfessorConsole getProfessorConsole() {
        return new ProfessorConsole(getProfessorService(), getCourseConsole(), getStudentConsole());
    }

    public static StudentConsole getStudentConsole() {
        return new StudentConsole(getStudentService(), getCourseConsole());
    }

    public static CourseConsole getCourseConsole() {
        return new CourseConsole(getCourseService());
    }
}