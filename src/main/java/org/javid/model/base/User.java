package org.javid.model.base;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type",
        discriminatorType = DiscriminatorType.INTEGER)
public abstract class User extends BaseEntity<Integer> {

    @Column(unique = true)
    private String username;
    private String password;
    protected String firstname;
    protected String lastname;
    protected Long nationalCode;

}
