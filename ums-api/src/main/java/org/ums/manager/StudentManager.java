package org.ums.manager;

import org.ums.domain.model.mutable.MutableStudent;
import org.ums.domain.model.immutable.Student;
import org.ums.enums.StudentStatus;

import java.util.List;

public interface StudentManager extends ContentManager<Student, MutableStudent, String> {
  public List<Student> getStudentListFromStudentsString(final String pStudents);

  public List<Student> getStudentByCourseIdAndSemesterIdForSeatPlanForCCI(String pCourseId,
      int pSemesterId);

  public List<Student> getStudentBySemesterIdAndExamDateForCCI(Integer pSemesterId, String pExamDate);

  public List<Student> getActiveStudents();

  public List<Student> getRegisteredStudents(int pSemesterId, int pExamType);

  public List<Student> getRegisteredStudents(int pGroupNo, int pSemesterId, int pExamType);

  public int updateStudentsAdviser(List<MutableStudent> pStudents);

  public int updateStudentsStatus(StudentStatus pStudentStatus, String pStudentId);

  public List<Student> getActiveStudentsByAdviser(String pTeacherId);

  public int getSize(final int pSemesterId, final int pProgramId);
}
