package org.javid.repository;

import org.javid.model.Student;
import org.javid.repository.base.CrudRepository;
import org.javid.repository.base.UserRepository;

public interface StudentRepository extends CrudRepository<Student, Integer>, UserRepository<Student> {
}
