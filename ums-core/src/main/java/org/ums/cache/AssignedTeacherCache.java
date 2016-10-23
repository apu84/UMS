package org.ums.cache;

import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.immutable.CourseTeacher;
import org.ums.enums.CourseCategory;
import org.ums.manager.AssignedTeacherManager;
import org.ums.manager.CacheManager;
import org.ums.util.CacheUtil;

import java.util.List;

public class AssignedTeacherCache<R extends Identifier<I> & LastModifier, M extends R, I, C extends AssignedTeacherManager<R, M, I>>
    extends ContentCache<R, M, I, C> implements AssignedTeacherManager<R, M, I> {

  private CacheManager<R, I> mCacheManager;

  public AssignedTeacherCache(final CacheManager<R, I> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<R, I> getCacheManager() {
    return mCacheManager;
  }

  @Override
  protected String getCacheKey(I pId) {
    return CacheUtil.getCacheKey(CourseTeacher.class, pId);
  }

  @Override
  public List<R> getAssignedTeachers(Integer pProgramId, Integer pSemesterId, String pOfferedBy) {
    return getManager().getAssignedTeachers(pProgramId, pSemesterId, pOfferedBy);
  }

  @Override
  public List<R> getAssignedTeachers(Integer pProgramId, Integer pSemesterId,
      CourseCategory pCourseCategory, String pOfferedBy) {
    return getManager().getAssignedTeachers(pProgramId, pSemesterId, pCourseCategory, pOfferedBy);
  }

  @Override
  public List<R> getAssignedTeachers(Integer pProgramId, Integer pSemesterId, Integer pYear,
      String pOfferedBy) {
    return getManager().getAssignedTeachers(pProgramId, pSemesterId, pYear, pOfferedBy);
  }

  @Override
  public List<R> getAssignedTeachers(Integer pProgramId, Integer pSemesterId, Integer pYear,
      CourseCategory pCourseCategory, String pOfferedBy) {
    return getManager().getAssignedTeachers(pProgramId, pSemesterId, pYear, pCourseCategory,
        pOfferedBy);
  }

  @Override
  public List<R> getAssignedTeachers(Integer pProgramId, Integer pSemesterId, Integer pYear,
      Integer pSemester, String pOfferedBy) {
    return getManager().getAssignedTeachers(pProgramId, pSemesterId, pYear, pSemester, pOfferedBy);
  }

  @Override
  public List<R> getAssignedTeachers(Integer pProgramId, Integer pSemesterId, Integer pYear,
      Integer pSemester, CourseCategory pCourseCategory, String pOfferedBy) {
    return getManager().getAssignedTeachers(pProgramId, pSemesterId, pYear, pSemester,
        pCourseCategory, pOfferedBy);
  }

  @Override
  public List<R> getAssignedTeachers(Integer pProgramId, Integer pSemesterId, String pCourseId,
      String pOfferedBy) {
    return getManager().getAssignedTeachers(pProgramId, pSemesterId, pCourseId, pOfferedBy);
  }

  @Override
  public List<R> getAssignedCourses(Integer pSemesterId, String pTeacherId) {
    return getManager().getAssignedCourses(pSemesterId, pTeacherId);
  }
}
