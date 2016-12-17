package org.ums.cache;

import org.ums.domain.model.immutable.AdmissionStudent;
import org.ums.domain.model.mutable.MutableAdmissionStudent;
import org.ums.manager.AdmissionStudentManager;
import org.ums.manager.CacheManager;
import org.ums.util.CacheUtil;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 12-Dec-16.
 */
public class AdmissionStudentCache extends
    ContentCache<AdmissionStudent, MutableAdmissionStudent, String, AdmissionStudentManager>
    implements AdmissionStudentManager {

  private CacheManager<AdmissionStudent, String> mCacheManager;

  public AdmissionStudentCache(CacheManager<AdmissionStudent, String> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<AdmissionStudent, String> getCacheManager() {
    return mCacheManager;
  }

  @Override
  protected String getCacheKey(String pId) {
    return CacheUtil.getCacheKey(AdmissionStudent.class, pId);
  }

  @Override
  public List<AdmissionStudent> getTaletalkData(int pSemesterId) {
    return getManager().getTaletalkData(pSemesterId);
  }



}
