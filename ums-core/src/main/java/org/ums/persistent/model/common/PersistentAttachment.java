package org.ums.persistent.model.common;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.common.MutableAttachment;
import org.ums.enums.ApplicationType;
import org.ums.manager.common.AttachmentManager;

/**
 * Created by Monjur-E-Morshed on 11-Jul-17.
 */
public class PersistentAttachment implements MutableAttachment {

  private static AttachmentManager sAttachmentManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sAttachmentManager = applicationContext.getBean("attachmentManager", AttachmentManager.class);
  }

  private Long mId;
  private ApplicationType mApplicationType;
  private String mApplicationId;
  private String mFileName;
  private String mLastModified;

  public PersistentAttachment() {

  }

  public PersistentAttachment(final PersistentAttachment pPersistentAttachment) {
    mId = pPersistentAttachment.getId();
    mApplicationType = pPersistentAttachment.getApplicationType();
    mApplicationId = pPersistentAttachment.getApplicationId();
    mFileName = pPersistentAttachment.getFileName();
    mLastModified = pPersistentAttachment.getLastModified();
  }

  @Override
  public MutableAttachment edit() {
    return new PersistentAttachment(this);
  }

  @Override
  public Long create() {
    return sAttachmentManager.create(this);
  }

  @Override
  public String getLastModified() {
    return mLastModified;
  }

  @Override
  public Long getId() {
    return mId;
  }

  @Override
  public void setId(Long pId) {
    mId = pId;
  }

  @Override
  public void setLastModified(String pLastModified) {
    mLastModified = pLastModified;
  }

  @Override
  public void update() {
    sAttachmentManager.update(this);
  }

  @Override
  public void delete() {
    sAttachmentManager.delete(this);
  }

  @Override
  public ApplicationType getApplicationType() {
    return mApplicationType;
  }

  @Override
  public void setApplicationType(ApplicationType pApplicationType) {
    mApplicationType = pApplicationType;
  }

  @Override
  public String getApplicationId() {
    return mApplicationId;
  }

  @Override
  public String getFileName() {
    return mFileName;
  }

  @Override
  public void setApplicationId(String pApplicationId) {
    mApplicationId = pApplicationId;
  }

  @Override
  public void setFileName(String pFileName) {
    mFileName = pFileName;
  }
}
