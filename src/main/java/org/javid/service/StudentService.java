package org.javid.service;

import org.javid.model.Student;
import org.javid.service.base.BaseService;
import org.javid.service.base.UserService;

public interface StudentService extends BaseService<Student, Integer>, UserService<Student> {
}
