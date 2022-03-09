package org.javid.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author javid
 * Created on 3/9/2022
 */

@Getter
@Setter
@Accessors(chain = true)
@Entity
@DiscriminatorValue("2")
public class StudentTerm extends Term {

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;

    @Override
    public StudentTerm setId(Integer integer) {
        super.setId(integer);
        return this;
    }

    @Override
    public StudentTerm setTermNumber(Integer termNumber) {
        super.setTermNumber(termNumber);
        return this;
    }
}
