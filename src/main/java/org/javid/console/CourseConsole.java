package org.javid.console;

import org.javid.Application;
import org.javid.console.base.BaseConsole;
import org.javid.model.Course;
import org.javid.model.base.BaseEntity;
import org.javid.service.CourseService;
import org.javid.util.Screen;

import java.util.Comparator;
import java.util.List;

public class CourseConsole extends BaseConsole<Course, Integer, CourseService> {

    public CourseConsole(CourseService service) {
        super(service);
    }

    public void manage() {
        while (true) {
            try {
                var choice = Screen.showMenu("Exit", List.of("Add Course", "Delete Course", "Edit Course"));

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
        var course = new Course()
                .setName(Screen.getString("Course name: "))
                .setUnit(Math.max(1, Screen.getInt("Course unit [>= 1]: ")))
                .setRequiredCourse(select("Select required course: "));

        if (Application.confirmMenu("Save Course") > 0) {
            service.save(course);
            System.out.println(course.isNew() ?
                    "Failed to save course!" :
                    "Course saved successfully.");
        }
    }

    public void showAll() {
        service.findAll()
                .stream()
                .sorted(Comparator.comparing(Course::getId))
                .map(Course::toString)
                .forEach(System.out::println);
    }

    public void delete() {
        var course = select("Select course to delete: ");
        if (course == null)
            return;
        service.deleteById(course.getId());
        System.out.println("Course deleted.");
    }

    private void update() {
        var course = select("Select course to update: ");
        if (course == null)
            return;

        var name = Screen.getString("Enter - or new name: ");
        if (Application.isForUpdate(name))
            course.setName(name);

        var unit = Screen.getInt("Enter 0 or new unit: ");
        if (unit >= 0)
            course.setUnit(unit);

        if (Application.confirmMenu("Want to change required course: ") > 0) {
            course.setRequiredCourse(select("Select new required course: "));
        }
        update(course);
        System.out.println("Course updated successfully.");
    }

    public void update(Course course) {
        service.update(course);
    }
}
