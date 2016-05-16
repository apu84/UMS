package org.ums.cache;

import org.ums.domain.model.immutable.CourseTeacher;
import org.ums.domain.model.mutable.MutableCourseTeacher;
import org.ums.enums.CourseCategory;
import org.ums.manager.CacheManager;
import org.ums.manager.CourseTeacherManager;
import org.ums.util.CacheUtil;

import java.util.List;

public class CourseTeacherCache extends ContentCache<CourseTeacher, MutableCourseTeacher, String, CourseTeacherManager> implements CourseTeacherManager {
  private CacheManager<CourseTeacher> mCacheManager;

  public CourseTeacherCache(final CacheManager<CourseTeacher> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<CourseTeacher> getCacheManager() {
    return mCacheManager;
  }

  @Override
  protected String getCacheKey(String pId) {
    return CacheUtil.getCacheKey(CourseTeacher.class, pId);
  }

  @Override
  public List<CourseTeacher> getCourseTeachers(String pCourseId, String pSemesterId) {
    return getManager().getCourseTeachers(pCourseId, pSemesterId);
  }

  @Override
  public List<CourseTeacher> getCourseTeachers(String pCourseId, String pSemesterId, Integer pYear, int pSemester) {
    return getManager().getCourseTeachers(pCourseId, pSemesterId, pYear, pSemester);
  }

  @Override
  public List<CourseTeacher> getCourseTeachers(Integer pProgramId, Integer pSemesterId) {
    return getManager().getCourseTeachers(pProgramId, pSemesterId);
  }

  @Override
  public List<CourseTeacher> getCourseTeachers(Integer pProgramId, Integer pSemesterId, CourseCategory pCourseCategory) {
    return getManager().getCourseTeachers(pProgramId, pSemesterId, pCourseCategory);
  }

  @Override
  public List<CourseTeacher> getCourseTeachers(Integer pProgramId, Integer pSemesterId, Integer pYear) {
    return getManager().getCourseTeachers(pProgramId, pSemesterId, pYear);
  }

  @Override
  public List<CourseTeacher> getCourseTeachers(Integer pProgramId, Integer pSemesterId, Integer pYear, CourseCategory pCourseCategory) {
    return getManager().getCourseTeachers(pProgramId, pSemesterId, pYear, pCourseCategory);
  }

  @Override
  public List<CourseTeacher> getCourseTeachers(Integer pProgramId, Integer pSemesterId, Integer pYear, Integer pSemester) {
    return getManager().getCourseTeachers(pProgramId, pSemesterId, pYear, pSemester);
  }

  @Override
  public List<CourseTeacher> getCourseTeachers(Integer pProgramId, Integer pSemesterId, Integer pYear, Integer pSemester, CourseCategory pCourseCategory) {
    return getManager().getCourseTeachers(pProgramId, pSemesterId, pYear, pSemester, pCourseCategory);
  }

  @Override
  public List<CourseTeacher> getCourseTeachers(Integer pProgramId, Integer pSemesterId, String pCourseId) {
    return  getManager().getCourseTeachers(pProgramId, pSemesterId,pCourseId);
  }
}