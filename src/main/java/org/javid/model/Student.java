package org.javid.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.javid.model.base.Person;
import org.javid.model.enums.Permit;

import java.util.*;

import static org.javid.model.enums.Permit.*;

@Getter
@Setter
@Accessors(chain = true)
public class Student extends Person {

    private static final Set<Permit> STUDENT_PERMITTs =  new HashSet<>(Arrays.asList(
            GET_STUDENT_I
            , READ_COURSE
            , REGISTER_COURSE
    ));

    private Integer studentCode;
    private int termNumber;
    // Map<termNumber, Map<Course, score>>
    private Map<Integer, Map<Course, Integer>> courses = new HashMap<>();

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
    public Student setPermissions(Set<Permit> permissions) {
        super.setPermissions(permissions);
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
