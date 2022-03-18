package org.javid.console;

import org.javid.Application;
import org.javid.console.base.UserConsole;
import org.javid.context.ApplicationContext;
import org.javid.model.Employee;
import org.javid.service.EmployeeService;
import org.javid.util.Screen;

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
        if ("admin".equals(currentUser.getUsername())) {
            adminMenu();
        } else {
            while (true) {
                try {
                    var choice = Screen.showMenu("Exit", List.of("See salary"));
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
                var choice = Screen.showMenu("Exit", List.of(
                        "Manage Employee", "Manage Professor"
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
                        ApplicationContext.getStudentConsole().manage();
                        break;
                    case 4:
                        ApplicationContext.getCourseConsole().manage();
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
                var choice = Screen.showMenu("Exit", List.of(
                        "Add Employee", "Delete Employee", "Edit Employee"));

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
        var employee = save();
        if (employee != null && Application.confirmMenu("Save employee") > 0) {
            service.save(employee);
            System.out.println(employee.isNew() ?
                    "Failed to save employee!" :
                    "Employee saved successfully.");
        }
    }

    @Override
    public Employee save() {
        var employee = super.save();
        return employee == null ? null
                : employee.setSalary(Screen.getLong("Salary: "));
    }

    @Override
    public Employee select(String message) {
        return select(message, service.findAll().stream()
                .filter(employee -> !"admin".equals(employee.getUsername()))
                .collect(Collectors.toList()));
    }

    public void delete() {
        var employee = select("Select employee to delete: ");
        if (employee == null)
            return;
        service.deleteById(employee.getId());
        System.out.println("Employee deleted.");
    }

    private void update() {
        var employee = super.update("Select employee to update: ");
        if (employee == null)
            return;

        var salary = Screen.getLong("Enter -1 or new salary: ");
        if (salary >= 0)
            employee.setSalary(salary);

        service.update(employee);
        System.out.println("Employee updated successfully.");
    }
}
