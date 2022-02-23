package org.javid.model;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.javid.model.enums.Permit;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static org.javid.model.enums.Permit.*;

@Getter
@Setter
@Accessors(chain = true)
public class Professor extends Employee {

    private static final Set<Permit> PROFESSOR_PERMITTs = new HashSet<>(Arrays.asList(
            READ_PROFESSOR
            , SCORE_STUDENT
            , SALARY_PROFESSOR_I
    ));

    private static long salaryPerCourse = 1_000_000L;
    private static long facultyMemberFee = 5_000_000L;
    private boolean facultyMember;
    private HashMap<Course, Integer> courses = new HashMap<>();

    @Override
    public Professor setId(Integer integer) {
        super.setId(integer);
        return this;
    }

    @Override
    public Professor setUsername(String username) {
        super.setUsername(username);
        return this;
    }

    @Override
    public Professor setPassword(String password) {
        super.setPassword(password);
        return this;
    }

    @Override
    public Professor setPermissions(Set<Permit> permissions) {
        super.setPermissions(permissions);
        return this;
    }

    @Override
    public Professor setFirstname(String firstname) {
        super.setFirstname(firstname);
        return this;
    }

    @Override
    public Professor setLastname(String lastname) {
        super.setLastname(lastname);
        return this;
    }

    @Override
    public Professor setNationalCode(Long nationalCode) {
        super.setNationalCode(nationalCode);
        return this;
    }

    @Override
    public Professor setSalary(Long salary) {
        super.setSalary(salary);
        return this;
    }

    @Override
    public String toString() {
        return "[ username:" + super.getUsername() +
                ", password:" + super.getPassword() +
                ", firstName:" + super.getFirstname() +
                ", lastName:" + super.getLastname() +
                ", nationalCode:" + super.getNationalCode() +
                " ]";
    }
}
