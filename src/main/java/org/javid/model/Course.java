package org.javid.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.javid.model.base.BaseEntity;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
public class Course extends BaseEntity<Integer> {

    private String name;
    private Integer unit;
    private Professor professor;
    private Course requiredCourse;
    private Set<Student> students = new HashSet<>();

    @Override
    public Course setId(Integer integer) {
        super.setId(integer);
        return this;
    }

    @Override
    public String toString() {
        return "[ codeName:" + getName() +
                ", unit:" + unit +
                ", requiredCourseId:" + (requiredCourse == null ? null : requiredCourse.getId()) +
                " ]";
    }
}
