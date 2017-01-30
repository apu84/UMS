package org.ums.decorator;

import org.ums.domain.model.mutable.MutableStudent;
import org.ums.domain.model.immutable.Student;
import org.ums.enums.StudentStatus;
import org.ums.manager.StudentManager;

import java.util.List;

public class StudentDaoDecorator extends
    ContentDaoDecorator<Student, MutableStudent, String, StudentManager> implements StudentManager {
  @Override
  public List<Student> getStudentListFromStudentsString(String pStudents) {
    return getManager().getStudentListFromStudentsString(pStudents);
  }

  @Override
  public List<Student> getStudentByCourseIdAndSemesterIdForSeatPlanForCCI(String pCourseId,
      int pSemesterId) {
    return getManager().getStudentByCourseIdAndSemesterIdForSeatPlanForCCI(pCourseId, pSemesterId);
  }

  @Override
  public List<Student> getStudentBySemesterIdAndExamDateForCCI(Integer pSemesterId, String pExamDate) {
    return getManager().getStudentBySemesterIdAndExamDateForCCI(pSemesterId, pExamDate);
  }

  @Override
  public List<Student> getActiveStudents() {
    return getManager().getActiveStudents();
  }

  @Override
  public int updateStudentsAdviser(List<MutableStudent> pStudents) {
    return getManager().updateStudentsAdviser(pStudents);
  }

  @Override
  public List<Student> getActiveStudentsByAdviser(String pTeacherId) {
    return getManager().getActiveStudentsByAdviser(pTeacherId);
  }

  @Override
  public List<Student> getRegisteredStudents(int pSemesterId, int pExamType) {
    return getManager().getRegisteredStudents(pSemesterId, pExamType);
  }

  @Override
  public List<Student> getRegisteredStudents(int pGroupNo, int pSemesterId, int pExamType) {
    return getManager().getRegisteredStudents(pGroupNo, pSemesterId, pExamType);
  }

  @Override
  public int getSize(int pSemesterId, int pProgramId) {
    return getManager().getSize(pSemesterId, pProgramId);
  }

  @Override
  public int updateStudentsStatus(StudentStatus pStudentStatus, String pStudentId) {
    return getManager().updateStudentsStatus(pStudentStatus, pStudentId);
  }
}
