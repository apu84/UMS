package org.ums.cache;

import org.ums.domain.model.immutable.AdmissionMeritList;
import org.ums.domain.model.immutable.Faculty;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.mutable.MutableAdmissionMeritList;
import org.ums.enums.QuotaType;
import org.ums.manager.AdmissionMeritListManager;
import org.ums.manager.CacheManager;
import org.ums.util.CacheUtil;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 10-Dec-16.
 */
public class AdmissionMeritListCache extends
    ContentCache<AdmissionMeritList, MutableAdmissionMeritList, Integer, AdmissionMeritListManager>
    implements AdmissionMeritListManager {

  private CacheManager<AdmissionMeritList, Integer> mCacheManager;

  public AdmissionMeritListCache(CacheManager<AdmissionMeritList, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  public List<AdmissionMeritList> getMeritList(Semester pSemester, Faculty pFaculty,
      QuotaType pAdmissionGroup) {
    return getManager().getMeritList(pSemester, pFaculty, pAdmissionGroup);
  }

  @Override
  protected CacheManager<AdmissionMeritList, Integer> getCacheManager() {
    return mCacheManager;
  }

}
