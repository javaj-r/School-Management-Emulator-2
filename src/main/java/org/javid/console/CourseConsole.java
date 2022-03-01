package org.javid.console;

import lombok.RequiredArgsConstructor;
import org.javid.Application;
import org.javid.model.Course;
import org.javid.model.Student;
import org.javid.service.CourseService;
import org.javid.util.Screen;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CourseConsole {

    private final CourseService service;

    public void manage() {
        while (true) {
            try {
                int choice = Screen.showMenu("Exit", Arrays.asList("Add Course", "Delete Course", "Edit Course"));

                if (choice == 0)
                    break;

                switch (choice) {
                    case 1:
                        save();
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

    private void save() {
        Course course = new Course()
                .setName(Screen.getString("Course name: "))
                .setUnit(Math.max(1, Screen.getInt("Course unit [>= 1]: ")))
                .setRequiredCourse(select("Select required course: "));

        if (Application.confirmMenu("Save Course") > 0) {
            System.out.println(service.save(course) != null ?
                    "Course saved successfully." :
                    "Failed to save course!");
        }
    }

    public void showAll() {
        service.findAll()
                .stream()
                .map(Course::toString)
                .forEach(System.out::println);
    }

    public void fetchStudentCourses(Student student) {
        service.findAllCoursesByStudentId(student);
    }

    public Course select(String message) {
        return select(message, service.findAll());
    }

    public Course select(String message, List<Course> courses) {
        List<String> items = courses.stream()
                .map(Course::toString)
                .collect(Collectors.toList());

        int choice = Screen.showMenu(message, "Cancel", items);
        if (choice == 0) {
            return new Course();
        }

        return courses.get(choice - 1);
    }

    public void delete() {
        Course course = select("Select course to delete: ");
        if (course.isNew())
            return;
        service.deleteById(course.getId());
        System.out.println("Course deleted.");
    }

    private void update() {
        Course course = select("Select course to update: ");
        if (course.isNew())
            return;

        String name = Screen.getString("Enter - or new name: ");
        if (Application.isForUpdate(name))
            course.setName(name);

        int unit = Screen.getInt("Enter 0 or new unit: ");
        if (unit >= 0)
            course.setUnit(unit);

        if (Application.confirmMenu("Want to change required course: ") > 0) {
            course.setRequiredCourse(select("Select new required course: "));
        }

        if (Application.confirmMenu("Save changes") > 0) {
            service.update(course);
            System.out.println("Course updated successfully.");
        }
    }
}
