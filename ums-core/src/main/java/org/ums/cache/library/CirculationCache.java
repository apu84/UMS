package org.ums.cache.library;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.library.Circulation;
import org.ums.domain.model.mutable.library.MutableCirculation;
import org.ums.manager.CacheManager;
import org.ums.manager.library.CirculationManager;

import java.util.List;

public class CirculationCache extends ContentCache<Circulation, MutableCirculation, Long, CirculationManager> implements
    CirculationManager {

  CacheManager<Circulation, Long> mCacheManager;

  public CirculationCache(CacheManager<Circulation, Long> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<Circulation, Long> getCacheManager() {
    return mCacheManager;
  }

  @Override
  public int saveCheckout(MutableCirculation pMutableCirculation) {
    return getManager().saveCheckout(pMutableCirculation);
  }

  @Override
  public List<Circulation> getCirculation(String pPatronId) {
    return getManager().getCirculation(pPatronId);
  }

  @Override
  public int updateCirculation(MutableCirculation pMutable) {
    return getManager().updateCirculation(pMutable);
  }

  @Override
  public int batchUpdateCirculation(List<MutableCirculation> pMutable) {
    return getManager().batchUpdateCirculation(pMutable);
  }

  @Override
  public List<Circulation> getAllCirculation(String pPatronId) {
    return getManager().getAllCirculation(pPatronId);
  }

  @Override
  public List<Circulation> getCirculationCheckedInItems(String pPatronId) {
    return getManager().getCirculationCheckedInItems(pPatronId);
  }

  @Override
  public Circulation getSingleCirculation(String pAccessionNumber) {
    return getManager().getSingleCirculation(pAccessionNumber);
  }
}
