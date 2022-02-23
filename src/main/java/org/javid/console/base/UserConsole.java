package org.javid.console.base;

import org.javid.model.base.User;
import org.javid.service.base.UserService;
import org.javid.util.Screen;

import java.util.Collections;

public abstract class UserConsole<T extends User, S extends UserService<T>> {

    protected final S service;
    protected T currentUser;

    public UserConsole(S service) {
        this.service = service;
    }

    public void login() {
        boolean loopFlag = true;

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

        user.setPassword(Screen.getPassword("Password: "));
        return user;
    }

    protected abstract T getNewInstance();

    public abstract void userMenu();

    public int tryAgainOrExit(String header) {
        return Screen.showMenu(header
                , "Select from menu: ", "Invalid choice."
                , "Cancel.", Collections.singletonList("Try again."));
    }
}
