package org.javid.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.javid.model.base.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@DiscriminatorValue("3")
public class Student extends User {

    private Integer studentCode;
    private int termNumber;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private Set<StudentTerm> terms = new HashSet<>();

    @Override
    public Student setId(Integer integer) {
        super.setId(integer);
        return this;
    }

    @Override
    public Student setUsername(String username) {
        super.setUsername(username);
        return this;
    }

    @Override
    public Student setPassword(String password) {
        super.setPassword(password);
        return this;
    }

    @Override
    public Student setFirstname(String firstname) {
        super.setFirstname(firstname);
        return this;
    }

    @Override
    public Student setLastname(String lastname) {
        super.setLastname(lastname);
        return this;
    }

    @Override
    public Student setNationalCode(Long nationalCode) {
        super.setNationalCode(nationalCode);
        return this;
    }

    @Override
    public String toString() {
        return "[ studentCode:" + getStudentCode() +
                ", termNumber:" + termNumber +
                ", username:" + super.getUsername() +
                ", password:" + super.getPassword() +
                ", firstName:" + super.getFirstname() +
                ", lastName:" + super.getLastname() +
                ", nationalCode:" + super.getNationalCode() +
                " ]";
    }
}
