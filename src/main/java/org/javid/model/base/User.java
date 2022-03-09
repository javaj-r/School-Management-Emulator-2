package org.javid.model.base;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@Accessors(chain = true)
@MappedSuperclass
public class User extends BaseEntity<Integer> {

    @Column(unique = true)
    private String username;
    private String password;

}
