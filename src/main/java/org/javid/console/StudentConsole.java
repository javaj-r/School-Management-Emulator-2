package org.javid.console;

import org.javid.Application;
import org.javid.console.base.PersonConsole;
import org.javid.model.Course;
import org.javid.model.Student;
import org.javid.service.StudentService;
import org.javid.util.Screen;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
                int choice = Screen.showMenu("Exit"
                        , Arrays.asList("My Information", "Show Courses", "Select Course", "My Courses"));

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
        courseConsole.fetchStudentCourses(currentUser);
        Map<Integer, Map<Course, Integer>> courses = currentUser.getCourses();
        int term = currentUser.getTermNumber();
        Course course = courseConsole.select("Select a course for adding to your courses: ");

        if (courses.containsKey(term)){
            if (courses.get(term).containsKey(course)) {
                System.out.println("Already selected");
                return;
            }

            if (term > 1) {
                int[] currentUnits = new int[]{0};
                courses.get(term).keySet().forEach(course1 -> currentUnits[0] += course1.getUnit());
                if (course.getUnit() + currentUnits[0] > getPermeatedUnits(courses, term)) {
                    System.out.println("Out of permeated units limit!");
                    return;
                }
            }
        }

        if (isPassedCourse(courses, course)) {
            System.out.println("You passed this course.");
            return;
        }

        if (course.getRequiredCourse() != null) {
            if (term == 1 || !isPassedRequiredCourse(courses, course, term)) {
                System.out.println("Course hase required course that you didn't passed yet!");
                return;
            }
        }

        service.saveStudentCourse(currentUser, course);
    }

    private int getPermeatedUnits(Map<Integer, Map<Course, Integer>> courses, int currentTerm) {
        if (currentTerm == 1)
            return 24;

        int[] scoreSum = new int[]{0};
        courses.get(currentTerm - 1).values().forEach(score -> scoreSum[0] += score);
        int avg = (scoreSum[0] / courses.get(currentTerm - 1).size());
        return avg >= 18 ? 24 : 20;
    }

    private boolean isPassedRequiredCourse(Map<Integer, Map<Course, Integer>> courses, Course course, int term){
        Map<Integer, Map<Course, Integer>> passedCourses = courses.entrySet()
                .stream().filter(integerMapEntry -> integerMapEntry.getKey() < term)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue));
        return isPassedCourse(passedCourses, course);
    }


    private boolean isPassedCourse(Map<Integer, Map<Course, Integer>> courses, Course course) {
        for (Map<Course, Integer> map : courses.values()) {
            for (Map.Entry<Course, Integer> entry:map.entrySet()) {
                if (course.equals(entry.getKey()) && entry.getValue()> 10) {
                    return true;
                }
            }
        }
        return false;
    }

    public void showStudentCourses() {
        courseConsole.fetchStudentCourses(currentUser);
        currentUser.getCourses()
                .entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry ->
                        entry.getValue()
                                .forEach((course, score) ->
                                        printStudentCourse(entry.getKey(), course, score)
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
                int choice = Screen.showMenu("Exit", Arrays.asList("Add Student", "Delete Student", "Edit Student"));

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
        Student student = save();
        if (student != null && Application.confirmMenu("Save Student") > 0) {
            System.out.println(service.save(student) != null ?
                    "Student saved successfully." :
                    "Failed to save student!");
        }
    }

    @Override
    public Student save() {
        Student student = super.save();
        return student == null ? null
                : student.setStudentCode(Screen.getInt("Student code: "))
                .setTermNumber(Math.max(1, Screen.getInt("Student term number [>= 1]: ")));
    }

    public Student select(String message) {
        return select(message, service.findAll());
    }

    public Student select(String message, List<Student> students) {
        List<String> items = students.stream()
                .map(Student::toString)
                .collect(Collectors.toList());

        int choice = Screen.showMenu(message, "Cancel", items);
        if (choice == 0) {
            return new Student();
        }

        return students.get(choice - 1);
    }

    public void delete() {
        Student student = select("Select student to delete: ");
        if (student.isNew())
            return;
        service.deleteById(student.getId());
        System.out.println("Student deleted.");
    }

    private void update() {
        Student student = select("Select student to update: ");
        if (student.isNew())
            return;

        String password = Screen.getString("Enter - or new password: ");
        if (Application.isForUpdate(password))
            student.setPassword(password);

        String firstname = Screen.getString("Enter - or new firstname: ");
        if (Application.isForUpdate(firstname))
            student.setFirstname(firstname);

        String lastname = Screen.getString("Enter - or new lastname: ");
        if (Application.isForUpdate(lastname))
            student.setLastname(lastname);

        long nationalCode = Screen.getLong("Enter -1 or new national code: ");
        if (nationalCode >= 0)
            student.setNationalCode(nationalCode);

        int studentCode = Screen.getInt("Enter -1 or new student code: ");
        if (studentCode >= 0)
            student.setStudentCode(studentCode);

        int termNumber = Screen.getInt("Enter 0 or new term number [>= 1]: ");
        if (termNumber > 0)
            student.setTermNumber(termNumber);

        if (Application.confirmMenu("Save changes") > 0) {
            service.update(student);
            System.out.println("Student updated successfully.");
        }
    }
}
