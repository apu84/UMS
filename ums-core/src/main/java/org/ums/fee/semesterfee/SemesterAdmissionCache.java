package org.ums.fee.semesterfee;

import java.util.Optional;

import org.ums.cache.ContentCache;
import org.ums.fee.UGFee;
import org.ums.manager.CacheManager;
import org.ums.util.CacheUtil;

public class SemesterAdmissionCache extends
    ContentCache<SemesterAdmissionStatus, MutableSemesterAdmissionStatus, Long, SemesterAdmissionStatusManager>
    implements SemesterAdmissionStatusManager {
  private CacheManager<SemesterAdmissionStatus, Long> mCacheManager;

  public SemesterAdmissionCache(final CacheManager<SemesterAdmissionStatus, Long> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  public Optional<SemesterAdmissionStatus> getAdmissionStatus(final String pStudentId, final Integer pSemesterId) {
    String cacheKey = getCacheKey(SemesterAdmissionStatus.class.toString(), pStudentId, pSemesterId);
    SemesterAdmissionStatus admissionStatus = cachedEntity(cacheKey, () -> {
      Optional<SemesterAdmissionStatus> status = getManager().getAdmissionStatus(pStudentId, pSemesterId);
      return status.isPresent() ? status.get() : null;
    });
    return admissionStatus != null? Optional.of(admissionStatus) : Optional.empty();
  }

  @Override
  public Optional<SemesterAdmissionStatus> lastAdmissionStatus(final String pStudentId) {
    String cacheKey = getCacheKey(SemesterAdmissionStatus.class.toString(), pStudentId);
    return Optional.of(cachedEntity(cacheKey, () -> {
      Optional<SemesterAdmissionStatus> status = getManager().lastAdmissionStatus(pStudentId);
      return status.isPresent() ? status.get() : null;
    }));
  }

  @Override
  protected CacheManager<SemesterAdmissionStatus, Long> getCacheManager() {
    return mCacheManager;
  }

  @Override
  protected String getCacheKey(Long pId) {
    return CacheUtil.getCacheKey(UGFee.class, pId);
  }
}
