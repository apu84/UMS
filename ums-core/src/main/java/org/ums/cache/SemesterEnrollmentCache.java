package org.ums.cache;

import org.ums.domain.model.immutable.SemesterEnrollment;
import org.ums.domain.model.mutable.MutableSemesterEnrollment;
import org.ums.manager.CacheManager;
import org.ums.manager.SemesterEnrollmentManager;
import org.ums.util.CacheUtil;

import java.util.List;

public class SemesterEnrollmentCache extends ContentCache<SemesterEnrollment, MutableSemesterEnrollment, Integer, SemesterEnrollmentManager>
    implements SemesterEnrollmentManager {
  private CacheManager<SemesterEnrollment> mCacheManager;

  public SemesterEnrollmentCache(final CacheManager<SemesterEnrollment> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<SemesterEnrollment> getCacheManager() {
    return mCacheManager;
  }

  @Override
  protected String getCacheKey(Integer pId) {
    return CacheUtil.getCacheKey(SemesterEnrollment.class, pId);
  }

  @Override
  public List<SemesterEnrollment> getEnrollmentStatus(SemesterEnrollment.Type pType, Integer pProgramId, Integer pSemesterId) {
    return getManager().getEnrollmentStatus(pType, pProgramId, pSemesterId);
  }

  @Override
  public SemesterEnrollment getEnrollmentStatus(SemesterEnrollment.Type pType, Integer pProgramId, Integer pSemesterId,
                                                Integer pYear, Integer pAcademicSemester) {
    return getManager().getEnrollmentStatus(pType, pProgramId, pSemesterId, pYear, pAcademicSemester);
  }

  @Override
  public List<SemesterEnrollment> getEnrollmentStatus(Integer pProgramId, Integer pSemesterId) {
    return getManager().getEnrollmentStatus(pProgramId, pSemesterId);
  }
}
