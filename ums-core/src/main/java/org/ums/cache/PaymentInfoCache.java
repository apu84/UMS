package org.ums.cache;

import org.ums.domain.model.immutable.PaymentInfo;
import org.ums.domain.model.mutable.MutablePaymentInfo;
import org.ums.manager.PaymentInfoManager;
import org.ums.manager.CacheManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 24-Jan-17.
 */
public class PaymentInfoCache extends ContentCache<PaymentInfo, MutablePaymentInfo, Integer, PaymentInfoManager>
    implements PaymentInfoManager {

  private CacheManager<PaymentInfo, Integer> mCacheManager;

  public PaymentInfoCache(CacheManager<PaymentInfo, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<PaymentInfo, Integer> getCacheManager() {
    return mCacheManager;
  }

  @Override
  public List<PaymentInfo> getPaymentInfo(String pReferenceId, int pSemesterId, String pQuota) {
    return getManager().getPaymentInfo(pReferenceId, pSemesterId, pQuota);
  }
}
