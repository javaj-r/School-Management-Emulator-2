package org.javid.console.base;

import org.javid.model.base.Person;
import org.javid.service.base.UserService;
import org.javid.util.Screen;

public abstract class PersonConsole<T extends Person, S extends UserService<T>> extends UserConsole<T, S> {

    public PersonConsole(S service) {
        super(service);
    }

    public T save() {
        T person = super.save();
        if(person == null)
            return null;

        person.setFirstname(Screen.getString("Firstname: "))
                .setLastname(Screen.getString("Lastname: "))
                .setNationalCode(Screen.getLong("National Code: "));

        return person;
    }
}
