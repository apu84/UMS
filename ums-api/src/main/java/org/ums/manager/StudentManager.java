package org.ums.manager;

import org.ums.domain.model.mutable.MutableStudent;
import org.ums.domain.model.readOnly.Student;

import java.util.List;


public interface StudentManager extends ContentManager<Student, MutableStudent, String> {

}
