package org.javid.model.base;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type",
        discriminatorType = DiscriminatorType.INTEGER)
public class Person extends User {

    protected String firstname;
    protected String lastname;
    protected Long nationalCode;
}
