package org.javid;

import org.javid.context.ApplicationContext;
import org.javid.model.Employee;
import org.javid.util.Screen;

import java.util.Arrays;
import java.util.Collections;

public class Application {

    public static void main(String[] args) {
        createAdmin();
        mainMenu();
    }

    private static void mainMenu() {

        while (true) {
            try {
                int choice = Screen.showMenu("Exit"
                        , Arrays.asList("Employee login", "Professor login", "Student login"));

                if (choice == 0)
                    break;

                switch (choice) {
                    case 1:
                        ApplicationContext.getEmployeeConsole().login();
                        break;
                    case 2:
                        ApplicationContext.getProfessorConsole().login();
                        break;
                    case 3:
                        ApplicationContext.getStudentConsole().login();
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void createAdmin() {
        ApplicationContext
                .getEmployeeRepository()
                .save(new Employee()
                        .setUsername("admin")
                        .setPassword("admin"));
    }


    public static int confirmMenu(String item) {
        return Screen.showMenu("Cancel", Collections.singletonList(item));
    }

    public static boolean isForUpdate(String element) {
        return !"-".equals(element.trim());
    }
}
