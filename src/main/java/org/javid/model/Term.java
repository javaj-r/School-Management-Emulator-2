package org.javid.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DiscriminatorFormula;
import org.javid.model.base.BaseEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author javid
 * Created on 3/9/2022
 */

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorFormula("CASE WHEN student_id IS NULL THEN 1 ELSE 2 END")
public class Term extends BaseEntity<Integer> {

    @Column(nullable = false)
    private Integer termNumber;

    @OneToMany(mappedBy = "term", cascade = CascadeType.ALL)
    private Set<TermCourse> termCourses = new HashSet<>();

}
