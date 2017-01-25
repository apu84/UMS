package org.ums.cache;

import org.ums.domain.model.immutable.PaymentInfo;
import org.ums.domain.model.mutable.MutablePaymentInfo;
import org.ums.manager.PaymentInfoManager;
import org.ums.manager.CacheManager;
import org.ums.util.CacheUtil;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 24-Jan-17.
 */
public class PaymentInfoCache extends
    ContentCache<PaymentInfo, MutablePaymentInfo, Integer, PaymentInfoManager> implements
    PaymentInfoManager {

  private CacheManager<PaymentInfo, Integer> mCacheManager;

  public PaymentInfoCache(CacheManager<PaymentInfo, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<PaymentInfo, Integer> getCacheManager() {
    return mCacheManager;
  }

  @Override
  protected String getCacheKey(Integer pId) {
    return CacheUtil.getCacheKey(PaymentInfo.class, pId);
  }

  @Override
  public List<PaymentInfo> getPaymentInfo(String pReferenceId, int pSemesterId) {
    return getManager().getPaymentInfo(pReferenceId, pSemesterId);
  }
}
