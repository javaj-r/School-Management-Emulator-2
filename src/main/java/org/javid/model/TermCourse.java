package org.javid.model;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.javid.model.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "term_course")
public class TermCourse extends BaseEntity<Integer> {

    private Integer score;

    @ManyToOne
    private Term term;

    @ManyToOne
    private Course course;

}
