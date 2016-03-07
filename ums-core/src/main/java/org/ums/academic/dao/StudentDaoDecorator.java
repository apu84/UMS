package org.ums.academic.dao;

import org.ums.domain.model.mutable.MutableStudent;
import org.ums.domain.model.readOnly.Student;
import org.ums.manager.StudentManager;


public class StudentDaoDecorator extends ContentDaoDecorator<Student, MutableStudent, String, StudentManager>
    implements StudentManager {

}
