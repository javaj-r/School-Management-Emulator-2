package org.javid.model.base;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.javid.model.enums.Permit;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
public class User extends BaseEntity<Integer> {

    private String username;
    private String password;
    private Set<Permit> permissions = new HashSet<>();

}
