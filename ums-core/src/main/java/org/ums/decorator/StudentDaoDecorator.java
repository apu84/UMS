package org.ums.decorator;

import org.ums.cache.ContentDaoDecorator;
import org.ums.domain.model.mutable.MutableStudent;
import org.ums.domain.model.immutable.Student;
import org.ums.manager.StudentManager;


public class StudentDaoDecorator extends ContentDaoDecorator<Student, MutableStudent, String, StudentManager>
    implements StudentManager {

}
