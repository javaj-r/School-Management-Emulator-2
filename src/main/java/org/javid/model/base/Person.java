package org.javid.model.base;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class Person extends User {

    protected String firstname;
    protected String lastname;
    protected Long nationalCode;
}
