package org.ums.cache;


import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.immutable.CourseTeacher;
import org.ums.enums.CourseCategory;
import org.ums.manager.AssignedTeacherManager;
import org.ums.manager.CacheManager;
import org.ums.util.CacheUtil;

import java.util.List;

public class AssignedTeacherCache<R extends Identifier<I> & LastModifier, M extends R, I>
    extends ContentCache<R, M, I, AssignedTeacherManager<R, M, I>>
    implements AssignedTeacherManager<R, M, I> {

  private CacheManager<R> mCacheManager;

  public AssignedTeacherCache(final CacheManager<R> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<R> getCacheManager() {
    return mCacheManager;
  }

  @Override
  protected String getCacheKey(I pId) {
    return CacheUtil.getCacheKey(CourseTeacher.class, pId);
  }

  @Override
  public List<R> getAssignedTeachers(Integer pProgramId, Integer pSemesterId) {
    return getManager().getAssignedTeachers(pProgramId, pSemesterId);
  }

  @Override
  public List<R> getAssignedTeachers(Integer pProgramId, Integer pSemesterId, CourseCategory pCourseCategory) {
    return getManager().getAssignedTeachers(pProgramId, pSemesterId, pCourseCategory);
  }

  @Override
  public List<R> getAssignedTeachers(Integer pProgramId, Integer pSemesterId, Integer pYear) {
    return getManager().getAssignedTeachers(pProgramId, pSemesterId, pYear);
  }

  @Override
  public List<R> getAssignedTeachers(Integer pProgramId, Integer pSemesterId, Integer pYear, CourseCategory pCourseCategory) {
    return getManager().getAssignedTeachers(pProgramId, pSemesterId, pYear, pCourseCategory);
  }

  @Override
  public List<R> getAssignedTeachers(Integer pProgramId, Integer pSemesterId, Integer pYear, Integer pSemester) {
    return getManager().getAssignedTeachers(pProgramId, pSemesterId, pYear, pSemester);
  }

  @Override
  public List<R> getAssignedTeachers(Integer pProgramId, Integer pSemesterId, Integer pYear, Integer pSemester, CourseCategory pCourseCategory) {
    return getManager().getAssignedTeachers(pProgramId, pSemesterId, pYear, pSemester, pCourseCategory);
  }

  @Override
  public List<R> getAssignedTeachers(Integer pProgramId, Integer pSemesterId, String pCourseId) {
    return getManager().getAssignedTeachers(pProgramId, pSemesterId, pCourseId);
  }
}
