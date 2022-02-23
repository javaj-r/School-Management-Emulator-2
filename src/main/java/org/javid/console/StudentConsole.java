package org.javid.console;

import org.javid.Application;
import org.javid.console.base.PersonConsole;
import org.javid.model.Student;
import org.javid.service.StudentService;
import org.javid.util.Screen;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StudentConsole extends PersonConsole<Student, StudentService> {

    public StudentConsole(StudentService service) {
        super(service);
    }

    @Override
    protected Student getNewInstance() {
        return new Student();
    }

    @Override
    public void userMenu() {
        System.out.println("Logged in");
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
        if (Application.confirmMenu("Save Student") > 0) {
            System.out.println(service.save(student) != null ?
                    "Student saved successfully." :
                    "Failed to save student!");
        }
    }

    @Override
    public Student save() {
        return super.save()
                .setStudentCode(Screen.getInt("Student code: "))
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
            student.setFirstname(lastname);

        long nationalCode = Screen.getLong("Enter -1 or new national code: ");
        if (nationalCode >= 0)
            student.setNationalCode(nationalCode);

        int studentCode = Screen.getInt("Enter -1 or new student code: ");
        if (studentCode >= 0)
            student.setStudentCode(studentCode);

        int termNumber = Screen.getInt("Enter 0 or new term number [>= 1]: ");
        if (termNumber > 0)
            student.setStudentCode(termNumber);

        if (Application.confirmMenu("Save changes") > 0) {
            service.update(student);
            System.out.println("Student updated successfully.");
        }
    }
}
