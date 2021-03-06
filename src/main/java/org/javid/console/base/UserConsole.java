package org.javid.console.base;

import org.javid.Application;
import org.javid.model.base.User;
import org.javid.service.base.BaseService;
import org.javid.service.base.UserService;
import org.javid.util.Screen;

import java.util.Collections;

public abstract class UserConsole<T extends User, S extends UserService<T> & BaseService<T, Integer>> extends BaseConsole<T, Integer, S> {

    protected T currentUser;

    public UserConsole(S service) {
        super(service);
    }

    public void login() {
        var loopFlag = true;

        while (loopFlag) {
            T user = getNewInstance();
            user.setUsername(Screen.getString("Username: "));
            user.setPassword(Screen.getPassword("Password: "));

            if ((currentUser = service.findByUsernameAndPassword(user)) != null) {
                userMenu();
                break;
            } else {
                int choice = tryAgainOrExit("Wrong Username or Password!");
                loopFlag = choice != 0;
            }
        }
    }

    public T save() {
        T user = getNewInstance();
        while (true) {
            user.setUsername(Screen.getString("Username: "));
            if (service.findByUsername(user) == null)
                break;

            if (tryAgainOrExit("Username already exists!") == 0)
                return null;
        }

        user.setPassword(Screen.getPassword("Password: "))
                .setFirstname(Screen.getString("Firstname: "))
                .setLastname(Screen.getString("Lastname: "))
                .setNationalCode(Screen.getLong("National Code: "));
        return user;
    }

    protected T update(String message) {
        var person = select(message);
        if (person == null)
            return null;

        var password = Screen.getString("Enter - or new password: ");
        if (Application.isForUpdate(password))
            person.setPassword(password);

        var firstname = Screen.getString("Enter - or new firstname: ");
        if (Application.isForUpdate(firstname))
            person.setFirstname(firstname);

        var lastname = Screen.getString("Enter - or new lastname: ");
        if (Application.isForUpdate(lastname))
            person.setLastname(lastname);

        var nationalCode = Screen.getLong("Enter -1 or new national code: ");
        if (nationalCode >= 0)
            person.setNationalCode(nationalCode);

        return person;
    }

    protected abstract T getNewInstance();

    public abstract void userMenu();

    public int tryAgainOrExit(String header) {
        return Screen.showMenu(header
                , "Select from menu: ", "Invalid choice."
                , "Cancel.", Collections.singletonList("Try again."));
    }
}
