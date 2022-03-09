package org.javid.console.base;

import org.javid.Application;
import org.javid.model.base.Person;
import org.javid.service.base.BaseService;
import org.javid.service.base.UserService;
import org.javid.util.Screen;

public abstract class PersonConsole<T extends Person, S extends UserService<T> & BaseService<T, Integer>> extends UserConsole<T, S> {

    public PersonConsole(S service) {
        super(service);
    }

    public T save() {
        T person = super.save();
        if (person == null)
            return null;

        person.setFirstname(Screen.getString("Firstname: "))
                .setLastname(Screen.getString("Lastname: "))
                .setNationalCode(Screen.getLong("National Code: "));

        return person;
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
}
