package org.ums.cache;

import java.util.List;

import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.mutable.MutableSemester;
import org.ums.enums.ProgramType;
import org.ums.enums.SemesterStatus;
import org.ums.manager.CacheManager;
import org.ums.manager.SemesterManager;

public class SemesterCache extends ContentCache<Semester, MutableSemester, Integer, SemesterManager> implements
    SemesterManager {
  @Override
  public List<Semester> getEnrolledSemesters(String pStudentId) {
    return getManager().getEnrolledSemesters(pStudentId);
  }

  private CacheManager<Semester, Integer> mCacheManager;

  public SemesterCache(final CacheManager<Semester, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<Semester, Integer> getCacheManager() {
    return mCacheManager;
  }

  @Override
  public List<Semester> getSemesters(Integer pProgramType, Integer pLimit) {
    return getManager().getSemesters(pProgramType, pLimit);
  }

  @Override
  public Semester getPreviousSemester(Integer pSemesterId, Integer pProgramTypeId) {
    return getManager().getPreviousSemester(pSemesterId, pProgramTypeId);
  }

  @Override
  public Semester getSemesterByStatus(ProgramType pProgramType, SemesterStatus status) {
    return getManager().getSemesterByStatus(pProgramType, status);
  }

  @Override
  public Semester getBySemesterName(String pSemesterName, Integer pProgramTypeId) {
    return getManager().getBySemesterName(pSemesterName, pProgramTypeId);
  }

  @Override
  public Semester getActiveSemester(Integer pProgramType) {
    return getManager().getActiveSemester(pProgramType);
  }

  @Override
  public List<Semester> getPreviousSemesters(Integer pSemesterId, Integer pProgramTypeId) {
    return getManager().getPreviousSemesters(pSemesterId, pProgramTypeId);
  }

  @Override
  public List<Semester> semestersAfter(Integer pStartSemester, Integer pEndSemester, Integer pProgramTypeId) {
    return getManager().semestersAfter(pStartSemester, pEndSemester, pProgramTypeId);
  }

  @Override
  public List<Semester> semestersAfter(Integer pStartSemester, Integer pProgramTypeId) {
    return getManager().semestersAfter(pStartSemester, pProgramTypeId);
  }

  @Override
  public Semester closestSemester(Integer pCheckSemester, List<Integer> pCheckAgainstSemesters) {
    return getManager().closestSemester(pCheckSemester, pCheckAgainstSemesters);
  }
}
