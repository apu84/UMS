package org.ums.manager;

import org.ums.domain.model.mutable.MutableStudent;
import org.ums.domain.model.immutable.Student;
import org.ums.enums.StudentStatus;
import org.ums.usermanagement.user.UserEmail;

import java.util.List;

public interface StudentManager extends ContentManager<Student, MutableStudent, String>, UserEmail<Student> {
  List<Student> getStudentListFromStudentsString(final String pStudents);

  List<Student> getStudentByCourseIdAndSemesterIdForSeatPlanForCCI(String pCourseId, int pSemesterId);

  List<Student> getStudentBySemesterIdAndExamDateForCCI(Integer pSemesterId, String pExamDate);

  List<Student> getActiveStudents();

  List<Student> getRegisteredStudents(int pSemesterId, int pExamType);

  List<Student> getRegisteredStudents(int pGroupNo, int pSemesterId, int pExamType);

  int updateStudentsAdviser(List<MutableStudent> pStudents);

  int updateStudentsSection(List<MutableStudent> pStudents);

  int updateStudentsStatus(StudentStatus pStudentStatus, String pStudentId);

  List<Student> getActiveStudentsByAdviser(String pTeacherId);

  int getSize(final int pSemesterId, final int pProgramId);
}
