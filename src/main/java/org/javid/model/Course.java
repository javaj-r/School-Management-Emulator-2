package org.javid.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.javid.model.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Getter
@Setter
@Accessors(chain = true)
@Entity
public class Course extends BaseEntity<Integer> {

    private String name;
    private Integer unit;

    @ManyToOne
    private Professor professor;

    @OneToOne
    @JoinColumn(name = "requiredCourse")
    private Course requiredCourse;

    @Override
    public Course setId(Integer integer) {
        super.setId(integer);
        return this;
    }

    @Override
    public String toString() {
        return "[ codeName:" + getName() +
                ", unit:" + unit +
                ", requiredCourseId:" + (requiredCourse == null ? null : requiredCourse.getName()) +
                ", Professor:" + (professor  == null ? null : professor.getFirstname() + " " + professor.getLastname()) +
                " ]";
    }
}
