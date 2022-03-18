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
@DiscriminatorValue("2")
public class Professor extends User {

    @Transient
    private static long salaryPerCourse = 1_000_000L;
    @Transient
    private static long facultyMemberFee = 5_000_000L;

    private boolean facultyMember;
    private int termNumber;

    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL)
    private Set<ProfessorTerm> terms = new HashSet<>();

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

    public Long getSalary(int termNumber) {
        var sum = terms.stream()
                .filter(professorTerm -> professorTerm.getTermNumber().equals(termNumber))
                .map(ProfessorTerm::getTermCourses)
                .flatMap(Set::stream)
                .map(TermCourse::getCourse)
                .mapToInt(Course::getUnit)
                .sum();
        long salary = sum * salaryPerCourse;

        return facultyMember ? salary + facultyMemberFee : salary;
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
