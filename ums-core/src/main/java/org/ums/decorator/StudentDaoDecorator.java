package org.ums.decorator;

import org.ums.domain.model.mutable.MutableStudent;
import org.ums.domain.model.immutable.Student;
import org.ums.manager.StudentManager;

import java.util.List;


public class StudentDaoDecorator extends ContentDaoDecorator<Student, MutableStudent, String, StudentManager>
    implements StudentManager {
    @Override
    public List<Student> getStudentListFromStudentsString(String pStudents) throws Exception {
        return getManager().getStudentListFromStudentsString(pStudents);
    }

}
