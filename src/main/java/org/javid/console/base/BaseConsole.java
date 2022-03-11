package org.javid.console.base;

import org.javid.model.base.BaseEntity;
import org.javid.service.base.BaseService;
import org.javid.util.Screen;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseConsole<T extends BaseEntity<ID>, ID extends Serializable & Comparable<ID>, S extends BaseService<T, ID>> {

    protected final S service;

    protected BaseConsole(S service) {
        this.service = service;
    }

    public T select(String message) {
        var entities = service.findAll()
                .stream()
                .sorted(Comparator.comparing(BaseEntity::getId))
                .collect(Collectors.toList());
        return select(message, entities);
    }

    public T select(String message, List<T> entities) {
        var items = entities
                .stream()
                .map(T::toString)
                .collect(Collectors.toList());

        var choice = Screen.showMenu(message, "Cancel", items);
        if (choice == 0)
            return null;

        return entities.get(choice - 1);
    }
}
