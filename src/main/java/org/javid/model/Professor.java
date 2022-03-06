package org.javid.model;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
public class Professor extends Employee {

    private static long salaryPerCourse = 1_000_000L;
    private static long facultyMemberFee = 5_000_000L;
    private boolean facultyMember;
    private HashMap<Integer, Set<Course>> courses = new HashMap<>();

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

    public Long getSalary(int termNumber) {
        long salary = 0;
        if (courses.containsKey(termNumber)) {
            salary = salaryPerCourse * getTermUnits(courses.get(termNumber));
            if (isFacultyMember())
                salary += facultyMemberFee;
        }
        return salary;
    }

    private int getTermUnits(Set<Course> termCourses) {
        int[] units = new int[]{0};
        termCourses.forEach(course -> units[0] += course.getUnit());
        return units[0];
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
