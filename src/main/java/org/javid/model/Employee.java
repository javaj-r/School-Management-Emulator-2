package org.javid.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.javid.model.base.User;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@DiscriminatorValue("1")
public class Employee extends User {

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
