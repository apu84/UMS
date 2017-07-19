package org.ums.cache.common;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.common.Attachment;
import org.ums.domain.model.mutable.common.MutableAttachment;
import org.ums.enums.ApplicationType;
import org.ums.manager.CacheManager;
import org.ums.manager.common.AttachmentManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 11-Jul-17.
 */
public class AttachmentCache extends ContentCache<Attachment, MutableAttachment, Long, AttachmentManager> implements
    AttachmentManager {

  private CacheManager<Attachment, Long> mCacheManager;

  public AttachmentCache(CacheManager<Attachment, Long> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<Attachment, Long> getCacheManager() {
    return mCacheManager;
  }

  @Override
  public List<Attachment> getAttachments(ApplicationType pApplicationType, String pApplicationId) {
    return getManager().getAttachments(pApplicationType, pApplicationId);
  }
}
