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

    @Override
    public List<Student> getStudentByCourseIdAndSemesterIdForSeatPlanForCCI(String pCourseId, int pSemesterId) {
        return getManager().getStudentByCourseIdAndSemesterIdForSeatPlanForCCI(pCourseId,pSemesterId);
    }

    @Override
    public List<Student> getStudentBySemesterIdAndExamDateForCCI(Integer pSemesterId, String pExamDate) {
        return getManager().getStudentBySemesterIdAndExamDateForCCI(pSemesterId,pExamDate);
    }

    @Override
    public List<Student> getActiveStudents() {
        return getManager().getActiveStudents();
    }
}
