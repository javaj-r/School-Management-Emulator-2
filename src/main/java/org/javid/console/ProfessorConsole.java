package org.javid.console;

import org.javid.Application;
import org.javid.console.base.PersonConsole;
import org.javid.model.Course;
import org.javid.model.Professor;
import org.javid.service.ProfessorService;
import org.javid.util.Screen;

import java.util.*;
import java.util.stream.Collectors;

public class ProfessorConsole extends PersonConsole<Professor, ProfessorService> {

    private final CourseConsole courseConsole;

    public ProfessorConsole(ProfessorService service, CourseConsole courseConsole) {
        super(service);
        this.courseConsole = courseConsole;
    }

    @Override
    protected Professor getNewInstance() {
        return new Professor();
    }

    @Override
    public void userMenu() {
        while (true) {
            try {
                int choice = Screen.showMenu("Exit"
                        , Arrays.asList("My Information", "Show Courses", "Select Course", "My Courses"));

                if (choice == 0)
                    break;

                switch (choice) {
                    case 1:
                        System.out.println(currentUser.toString());
                        break;
                    case 2:
//                        courseConsole.showAll();
                        break;
                    case 3:
//                        selectCourse();
                        break;
                    case 4:
//                        showStudentCourses();
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void manage() {
        while (true) {
            try {
                int choice = Screen.showMenu("Exit", Arrays.asList("Add Professor", "Delete Professor"
                        , "Edit Professor", "Select Course For Professor"));

                if (choice == 0)
                    break;

                switch (choice) {
                    case 1:
                        saveProfessor();
                        break;
                    case 2:
                        delete();
                        break;
                    case 3:
                        update();
                        break;
                    case 4:
                        addProfessorCourse();
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void addProfessorCourse() {
        Professor professor = select("Select professor: ");
        courseConsole.fetchProfessorCourses(professor);
        HashMap<Integer, Set<Course>> courses = professor.getCourses();
        int term = Screen.getInt("Enter Term Number: ");
        Course course = courseConsole.select("Select course: ");

        if (courses.containsKey(term)) {
            if (courses.get(term).contains(course)) {
                System.out.println("Already added");
                return;
            }
        }

        service.saveProfessorCourse(professor, course, term);
    }

    private void saveProfessor() {
        Professor professor = save();
        if (professor != null && Application.confirmMenu("Save professor") > 0) {
            System.out.println(service.save(professor) != null ?
                    "Professor saved successfully." :
                    "Failed to save professor!");
        }
    }

    @Override
    public Professor save() {
        Professor professor = super.save();
        if (professor == null)
            return null;

        professor.setSalary(Screen.getLong("Salary: "));
        int choice = Screen.showMenu("Faculty member: ", "No", Collections.singletonList("Yes"));
        return professor.setFacultyMember(choice == 1);
    }

    public Professor select(String message) {
        return select(message, service.findAll());
    }

    public Professor select(String message, List<Professor> professors) {
        List<String> items = professors.stream()
                .map(Professor::toString)
                .collect(Collectors.toList());

        int choice = Screen.showMenu(message, "Cancel", items);
        if (choice == 0) {
            return new Professor();
        }

        return professors.get(choice - 1);
    }

    public void delete() {
        Professor professor = select("Select professor to delete: ");
        if (professor.isNew())
            return;
        service.deleteById(professor.getId());
        System.out.println("Professor deleted.");
    }

    private void update() {
        Professor professor = select("Select professor to update: ");
        if (professor.isNew())
            return;

        String password = Screen.getString("Enter - or new password: ");
        if (Application.isForUpdate(password))
            professor.setPassword(password);

        String firstname = Screen.getString("Enter - or new firstname: ");
        if (Application.isForUpdate(firstname))
            professor.setFirstname(firstname);

        String lastname = Screen.getString("Enter - or new lastname: ");
        if (Application.isForUpdate(lastname))
            professor.setLastname(lastname);

        long nationalCode = Screen.getLong("Enter -1 or new national code: ");
        if (nationalCode >= 0)
            professor.setNationalCode(nationalCode);

        long salary = Screen.getLong("Enter -1 or new salary: ");
        if (salary >= 0)
            professor.setSalary(salary);

        int choice = Screen.showMenu("Faculty member: ", "Cancel", Arrays.asList("Yes", "No"));
        if (choice != 0)
            professor.setFacultyMember(choice == 1);

        if (Application.confirmMenu("Save changes") > 0) {
            service.update(professor);
            System.out.println("Professor updated successfully.");
        }
    }
}
