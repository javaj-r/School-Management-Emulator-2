package org.javid.console;

import org.javid.Application;
import org.javid.console.base.UserConsole;
import org.javid.context.ApplicationContext;
import org.javid.model.Employee;
import org.javid.service.EmployeeService;
import org.javid.util.Screen;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeConsole extends UserConsole<Employee, EmployeeService> {

    public EmployeeConsole(EmployeeService service) {
        super(service);
    }

    @Override
    protected Employee getNewInstance() {
        return new Employee();
    }

    @Override
    public void userMenu() {
        List<String> list = Arrays.asList("Add Student", "Delete Student", "Edit Student"
                , "Add Course", "Delete Course", "Edit Course");

        if ("admin".equals(currentUser.getUsername())) {
            adminMenu();
        } else {
            while (true) {
                try {
                    int choice = Screen.showMenu("Exit", Collections.singletonList("See salary"));
                    if (choice == 0) {
                        currentUser = null;
                        break;
                    } else if (choice == 1) {
                        System.out.println(currentUser.getSalary());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void adminMenu() {
        while (true) {
            try {
                int choice = Screen.showMenu("Exit", Arrays.asList("Manage Employee", "Manage Professor"
                        , "Manage Student", "Manage Course"));
                if (choice == 0) {
                    currentUser = null;
                    break;
                }

                switch (choice) {
                    case 1:
                        manage();
                        break;
                    case 2:
                        ApplicationContext.getProfessorConsole().manage();
                        break;
                    case 3:
//                        ApplicationContext.getCustomerConsole().login();
                        break;
                    case 4:
//                        ApplicationContext.getCustomerConsole().login();
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
                int choice = Screen.showMenu("Exit", Arrays.asList("Add Employee", "Delete Employee", "Edit Employee"));

                if (choice == 0)
                    break;

                switch (choice) {
                    case 1:
                        saveEmployee();
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

    private void saveEmployee() {
        Employee employee = save();
        if (Application.confirmMenu("Save employee") > 0) {
            System.out.println(service.save(employee) != null ?
                    "Employee saved successfully." :
                    "Failed to save employee!");
        }
    }

    @Override
    public Employee save() {
        return super.save().setSalary(Screen.getLong("Salary: "));
    }

    public Employee select(String message) {
        return select(message, service.findAll().stream()
                .filter(employee -> !"admin".equals(employee.getUsername()))
                .collect(Collectors.toList()));
    }

    public Employee select(String message, List<Employee> employees) {
        List<String> items = employees.stream()
                .map(Employee::toString)
                .collect(Collectors.toList());

        int choice = Screen.showMenu(message, "Cancel", items);
        if (choice == 0) {
            return new Employee();
        }

        return employees.get(choice - 1);
    }

    public void delete() {
        Employee employee = select("Select employee to delete: ");
        if (employee.isNew())
            return;
        service.deleteById(employee.getId());
        System.out.println("Employee deleted.");
    }

    private void update() {
        Employee employee = select("Select employee to update: ");
        if (employee.isNew())
            return;

        String password = Screen.getString("Enter - or new password: ");
        if (Application.isForUpdate(password))
            employee.setPassword(password);

        String firstname = Screen.getString("Enter - or new firstname: ");
        if (Application.isForUpdate(firstname))
            employee.setFirstname(firstname);

        String lastname = Screen.getString("Enter - or new lastname: ");
        if (Application.isForUpdate(lastname))
            employee.setFirstname(lastname);

        long nationalCode = Screen.getLong("Enter -1 or new national code: ");
        if (nationalCode >= 0)
            employee.setNationalCode(nationalCode);

        long salary = Screen.getLong("Enter -1 or new salary: ");
        if (salary >= 0)
            employee.setSalary(salary);

        if (Application.confirmMenu("Save changes") > 0) {
            service.update(employee);
            System.out.println("Employee updated successfully.");
        }
    }
}
