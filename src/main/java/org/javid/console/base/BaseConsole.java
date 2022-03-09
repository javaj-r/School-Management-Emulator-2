package org.javid.console.base;

import org.javid.model.base.BaseEntity;
import org.javid.service.base.BaseService;
import org.javid.util.Screen;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseConsole<T extends BaseEntity<ID>, ID extends Serializable, S extends BaseService<T, ID>> {

    protected final S service;

    protected BaseConsole(S service) {
        this.service = service;
    }

    public T select(String message) {
        return select(message, service.findAll());
    }

    public T select(String message, List<T> courses) {
        var items = courses.stream()
                .map(T::toString)
                .collect(Collectors.toList());

        var choice = Screen.showMenu(message, "Cancel", items);
        if (choice == 0) {
            return null;
        }

        return service.findById(courses.get(choice - 1).getId());
    }
}
