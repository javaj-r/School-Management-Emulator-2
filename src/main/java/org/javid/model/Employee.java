package org.javid.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.javid.model.base.Person;
import org.javid.model.enums.Permit;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.javid.model.enums.Permit.*;

@Getter
@Setter
@Accessors(chain = true)
public class Employee extends Person {
    private static final Set<Permit> EMPLOYEE_PERMITTs = new HashSet<>(Arrays.asList(
                    CREATE_STUDENT
                    , DELETE_STUDENT
                    , READ_STUDENT
                    , UPDATE_STUDENT

                    , CREATE_PROFESSOR
                    , DELETE_PROFESSOR
                    , READ_PROFESSOR
                    , UPDATE_PROFESSOR

                    , CREATE_EMPLOYEE
                    , DELETE_EMPLOYEE
                    , READ_EMPLOYEE
                    , UPDATE_EMPLOYEE

                    , CREATE_COURSE
                    , DELETE_COURSE
                    , READ_COURSE
                    , UPDATE_COURSE
            ));

    private Long salary;

    @Override
    public Employee setId(Integer integer) {
        super.setId(integer);
        return this;
    }

    @Override
    public Employee setUsername(String username) {
        super.setUsername(username);
        return this;
    }

    @Override
    public Employee setPassword(String password) {
        super.setPassword(password);
        return this;
    }

    @Override
    public Employee setPermissions(Set<Permit> permissions) {
        super.setPermissions(permissions);
        return this;
    }

    @Override
    public Employee setFirstname(String firstname) {
        super.setFirstname(firstname);
        return this;
    }

    @Override
    public Employee setLastname(String lastname) {
        super.setLastname(lastname);
        return this;
    }

    @Override
    public Employee setNationalCode(Long nationalCode) {
        super.setNationalCode(nationalCode);
        return this;
    }

    @Override
    public String toString() {
        return "[ username:" + super.getUsername() +
                ", password:" + super.getPassword() +
                ", firstName:" + super.getFirstname() +
                ", lastName:" + super.getLastname() +
                ", nationalCode:" + super.getNationalCode() +
                ", salary:" + getSalary() +
                " ]";

    }
}
