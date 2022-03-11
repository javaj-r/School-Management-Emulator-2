package org.javid.console;

import org.javid.Application;
import org.javid.console.base.PersonConsole;
import org.javid.model.*;
import org.javid.service.StudentService;
import org.javid.util.Screen;

import java.util.*;
import java.util.stream.Collectors;

public class StudentConsole extends PersonConsole<Student, StudentService> {

    private final CourseConsole courseConsole;

    public StudentConsole(StudentService service, CourseConsole courseConsole) {
        super(service);
        this.courseConsole = courseConsole;
    }

    @Override
    protected Student getNewInstance() {
        return new Student();
    }

    @Override
    public void userMenu() {
        while (true) {
            try {
                var choice = Screen.showMenu("Exit"
                        , List.of("My Information", "Show Courses", "Select Course", "My Courses"));

                if (choice == 0)
                    break;

                switch (choice) {
                    case 1:
                        System.out.println(currentUser.toString());
                        break;
                    case 2:
                        courseConsole.showAll();
                        break;
                    case 3:
                        selectCourse();
                        break;
                    case 4:
                        showStudentCourses();
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void selectCourse() {
        var course = courseConsole.select("Select a course for adding to your courses: ");
        if (course == null)
            return;

        var termNumber = currentUser.getTermNumber();
        var terms = currentUser.getTerms();
        var term = getTerm(currentUser, termNumber);
        if (term == null) {
            System.out.println("Term not found!");
            return;
        }

        var currentCourses = term.getTermCourses()
                .stream()
                .map(TermCourse::getCourse)
                .collect(Collectors.toList());
        if (currentCourses.contains(course)) {
            System.out.println("Already selected");
            return;
        }

        if (termNumber > 1 && isOutOfUnitLimit(course, termNumber, currentUser, term))
            return;

        if (isPassedCourse(terms, course)) {
            System.out.println("You passed this course.");
            return;
        }

        if (course.getRequiredCourse() != null) {
            if (termNumber == 1 || !isPassedCourse(terms, course.getRequiredCourse())) {
                System.out.println("Course hase required course that you didn't passed yet!");
                return;
            }
        }

        term.getTermCourses()
                .add(new TermCourse()
                        .setCourse(course)
                        .setTerm(term));
        service.update(currentUser);
    }

    private StudentTerm getTerm(Student student, int termNumber) {
        return student.getTerms()
                .stream()
                .filter(t -> t.getTermNumber().equals(termNumber))
                .findFirst()
                .orElse(null);
    }

    private boolean isOutOfUnitLimit(Course course, int termNumber, Student student, StudentTerm currentTerm) {
        var sum = currentTerm.getTermCourses()
                .stream()
                .map(TermCourse::getCourse)
                .mapToInt(Course::getUnit)
                .sum();
        if (course.getUnit() + sum > getPermeatedUnits(student, termNumber)) {
            System.out.println("Out of permeated units limit!");
            return true;
        }
        return false;
    }

    private int getPermeatedUnits(Student student, int currentTerm) {
        if (currentTerm == 1)
            return 24;

        var lastTerm = getTerm(student, currentTerm - 1);
        if (lastTerm == null) {
            System.out.println("Term not found!");
            return 20;
        }

        var avg = lastTerm.getTermCourses().stream()
                .mapToInt(TermCourse::getScore)
                .average()
                .orElse(0);

        return avg >= 18 ? 24 : 20;
    }

    private boolean isPassedCourse(Set<StudentTerm> terms, Course course) {
        return terms.stream()
                .map(Term::getTermCourses)
                .flatMap(Set::stream)
                .filter(tc -> Objects.equals(tc.getCourse().getId(), course.getId()))
                .filter(tc -> tc.getScore() != null)
                .anyMatch(tc -> tc.getScore() >= 10);
    }

    public void showStudentCourses() {
        currentUser.getTerms()
                .stream()
                .sorted(Comparator.comparing(Term::getTermNumber))
                .forEach(st -> st.getTermCourses()
                        .forEach(tc -> printStudentCourse(st.getTermNumber()
                                , tc.getCourse(), tc.getScore())
                        ));
    }

    private void printStudentCourse(Integer termNumber, Course course, Integer score) {
        System.out.println("[ Term:" + termNumber +
                ", Course: " + course.getName() +
                ", Score:" + score + " ]");
    }

    public void manage() {
        while (true) {
            try {
                var choice = Screen.showMenu("Exit", List.of("Add Student", "Delete Student", "Edit Student"));

                if (choice == 0)
                    break;

                switch (choice) {
                    case 1:
                        saveStudent();
                        break;
                    case 2:
                        delete();
                        break;
                    case 3:
                        update();
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void saveStudent() {
        var student = save();
        if (student != null && Application.confirmMenu("Save Student") > 0) {
            service.save(student);
            System.out.println(student.isNew() ?
                    "Failed to save student!" :
                    "Student saved successfully.");
        }
    }

    @Override
    public Student save() {
        var student = super.save();
        if (student == null)
            return null;

        var term = new StudentTerm()
                .setTermNumber(1)
                .setStudent(student);

        student.getTerms().add(term);

        return student
                .setTermNumber(1)
                .setStudentCode(Screen.getInt("Student code: "));
    }

    public void delete() {
        var student = select("Select student to delete: ");
        if (student == null)
            return;
        service.deleteById(student.getId());
        System.out.println("Student deleted.");
    }

    private void update() {
        var student = super.update("Select student to update: ");
        if (student == null)
            return;

        var studentCode = Screen.getInt("Enter -1 or new student code: ");
        if (studentCode >= 0)
            student.setStudentCode(studentCode);

        var choice = Screen.showMenu("New Term: ", "No", Collections.singletonList("Yes"));
        if (choice != 0) {
            student.setTermNumber(student.getTermNumber() + 1);
            var term = new StudentTerm()
                    .setTermNumber(student.getTermNumber())
                    .setStudent(student);

            student.getTerms().add(term);
        }

        service.update(student);
        System.out.println("Student updated successfully.");
    }

    public void updateStudentCourseScore(Professor professor) {
        var student = select("Select Student: ");
        if (student == null)
            return;
        var termCourse = selectTermCourse("Select Course: ", student, professor);
        if (termCourse == null)
            return;
        var score = getScore();
        if (score == -1)
            return;

        termCourse.setScore(score);
        service.save(student);
    }

    private int getScore() {
        var loopFlag = true;
        while (loopFlag) {
            var score = Screen.getInt("Enter score: ");
            if (score >= 0 && score <= 20)
                return score;

            var choice = tryAgainOrExit("Invalid score");
            loopFlag = choice != 0;
        }
        return -1;
    }

    public TermCourse selectTermCourse(String message, Student student, Professor professor) {
        var termCourses = student.getTerms().stream()
                .filter(term -> term.getTermNumber().equals(student.getTermNumber()))
                .map(Term::getTermCourses)
                .flatMap(Set::stream)
                .filter(ts -> professor.equals(ts.getCourse().getProfessor()))
                .collect(Collectors.toList());
        var items = termCourses.stream()
                .map(TermCourse::getCourse)
                .map(Course::toString)
                .collect(Collectors.toList());
        var choice = Screen.showMenu(message, "Cancel", items);
        if (choice == 0)
            return null;

        return termCourses.get(choice - 1);
    }
}
