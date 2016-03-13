package org.ums.manager;

import org.ums.domain.model.mutable.MutableStudent;
import org.ums.domain.model.immutable.Student;


public interface StudentManager extends ContentManager<Student, MutableStudent, String> {

}
