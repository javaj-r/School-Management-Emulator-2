package org.javid.console;

import org.javid.Application;
import org.javid.console.base.UserConsole;
import org.javid.model.Professor;
import org.javid.model.ProfessorTerm;
import org.javid.model.TermCourse;
import org.javid.service.ProfessorService;
import org.javid.util.Screen;

import java.util.Collections;
import java.util.List;

public class ProfessorConsole extends UserConsole<Professor, ProfessorService> {

    private final CourseConsole courseConsole;
    private final StudentConsole studentConsole;

    public ProfessorConsole(ProfessorService service, CourseConsole courseConsole, StudentConsole studentConsole) {
        super(service);
        this.courseConsole = courseConsole;
        this.studentConsole = studentConsole;
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
                        , List.of("My Information", "Score Students", "See salary"));

                if (choice == 0)
                    break;

                switch (choice) {
                    case 1:
                        System.out.println(currentUser.toString());
                        break;
                    case 2:
                        studentConsole.updateStudentCourseScore(currentUser);
                        break;
                    case 3:
                        printSalary();
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void printSalary() {
        int term = Screen.getInt("Enter term number: ");
        System.out.println(currentUser.getSalary(term));
    }

    public void manage() {
        while (true) {
            try {
                int choice = Screen.showMenu("Exit", List.of("Add Professor", "Delete Professor"
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
        var professor = select("Select professor: ");
        if (professor == null)
            return;
        var course = courseConsole.select("Select course: ");
        if (course == null)
            return;
        if (course.getProfessor() != null) {
            var check = course.getProfessor().equals(professor);
            System.out.println(check ? "Already selected" : "Selected for other professor");
            return;
        }
        var termNumber = professor.getTermNumber();
        var terms = professor.getTerms();
        var term = terms.stream()
                .filter(t -> t.getTermNumber().equals(termNumber))
                .findFirst()
                .orElse(null);

        if (term == null) {
            System.out.println("Term not found!");
            return;
        }

        course.setProfessor(professor);
        term.getTermCourses()
                .add(new TermCourse()
                        .setCourse(course)
                        .setTerm(term));
        courseConsole.update(course);
        service.update(professor);
    }

    private void saveProfessor() {
        var professor = save();
        if (professor != null && Application.confirmMenu("Save professor") > 0) {
            service.save(professor);
            System.out.println(professor.isNew() ?
                    "Failed to save professor!" :
                    "Professor saved successfully.");
        }
    }

    @Override
    public Professor save() {
        var professor = super.save();
        if (professor == null)
            return null;

        var term = new ProfessorTerm()
                .setTermNumber(1)
                .setProfessor(professor);
        professor.getTerms().add(term);
        var choice = Screen.showMenu("Faculty member: "
                , "No", Collections.singletonList("Yes"));

        return professor
                .setTermNumber(1)
                .setFacultyMember(choice == 1);
    }

    public void delete() {
        var professor = select("Select professor to delete: ");
        if (professor == null)
            return;
        service.deleteById(professor.getId());
        System.out.println("Professor deleted.");
    }

    private void update() {
        var professor = super.update("Select professor to update: ");
        if (professor == null)
            return;

        var choice = Screen.showMenu("Faculty member: ", "Cancel", List.of("Yes", "No"));
        if (choice != 0)
            professor.setFacultyMember(choice == 1);

        choice = Screen.showMenu("New Term: "
                , "No", Collections.singletonList("Yes"));
        if (choice != 0) {
            professor.setTermNumber(professor.getTermNumber() + 1);
            var term = new ProfessorTerm()
                    .setTermNumber(professor.getTermNumber())
                    .setProfessor(professor);

            professor.getTerms().add(term);
        }

        service.update(professor);
        System.out.println("Professor updated successfully.");
    }
}
