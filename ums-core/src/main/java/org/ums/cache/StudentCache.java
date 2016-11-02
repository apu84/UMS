package org.ums.cache;

import org.ums.domain.model.immutable.Student;
import org.ums.domain.model.mutable.MutableStudent;
import org.ums.manager.CacheManager;
import org.ums.manager.StudentManager;
import org.ums.util.CacheUtil;

import java.util.List;

public class StudentCache extends ContentCache<Student, MutableStudent, String, StudentManager>
    implements StudentManager {
  private CacheManager<Student, String> mCacheManager;

  public StudentCache(final CacheManager<Student, String> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<Student, String> getCacheManager() {
    return mCacheManager;
  }

  @Override
  protected String getCacheKey(String pId) {
    return CacheUtil.getCacheKey(Student.class, pId);
  }

  @Override
  public List<Student> getStudentListFromStudentsString(String pStudents) throws Exception {
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
  public int updateStudentsAdviser(List<MutableStudent> pStudents) throws Exception {
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
}
