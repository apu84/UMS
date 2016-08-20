package org.ums.persistent.model;


import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.User;
import org.ums.domain.model.mutable.MutableNotification;
import org.ums.manager.NotificationManager;
import org.ums.manager.UserManager;

import java.util.Date;

public class PersistentNotification implements MutableNotification {
  private static NotificationManager sNotificationManager;
  private static UserManager sUserManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sNotificationManager = applicationContext.getBean("notificationManager", NotificationManager.class);
    sUserManager = applicationContext.getBean("userManager", UserManager.class);
  }

  private Integer mId;
  private String mNotificationType;
  private String mPayload;
  private Date mProducedOn;
  private Date mConsumedOn;
  private String mLastModified;
  private String mProducerId;
  private String mConsumerId;

  public PersistentNotification()  {
  }

  public PersistentNotification(final PersistentNotification pPersistentNotification) throws Exception{
    setProducerId(pPersistentNotification.getProducerId());
    setConsumerId(pPersistentNotification.getConsumerId());
    setNotificationType(pPersistentNotification.getNotificationType());
    setPayload(pPersistentNotification.getPayload());
    setProducedOn(pPersistentNotification.getProducedOn());
    setConsumedOn(pPersistentNotification.getConsumedOn());
    setId(pPersistentNotification.getId());
    setLastModified(pPersistentNotification.getLastModified());
  }

  @Override
  public void setProducerId(String pProducerId) {
    mProducerId = pProducerId;
  }

  @Override
  public void setConsumerId(String pConsumerId) {
    mConsumerId = pConsumerId;
  }

  @Override
  public void setNotificationType(String pNotificationType) {
    mNotificationType = pNotificationType;
  }

  @Override
  public void setPayload(String pPayload) {
    mPayload = pPayload;
  }

  @Override
  public void setProducedOn(Date pProducedOn) {
    mProducedOn = pProducedOn;
  }

  @Override
  public void setConsumedOn(Date pConsumedOn) {
    mConsumedOn = pConsumedOn;
  }

  @Override
  public void commit(boolean update) throws Exception {
    if (update) {
      sNotificationManager.update(this);
    } else {
      sNotificationManager.create(this);
    }
  }

  @Override
  public void setId(Integer pId) {
    mId = pId;
  }

  @Override
  public String getProducerId() {
    return mProducerId;
  }

  @Override
  public String getConsumerId() {
    return mConsumerId;
  }

  @Override
  public String getNotificationType() {
    return mNotificationType;
  }

  @Override
  public String getPayload() {
    return mPayload;
  }

  @Override
  public Date getProducedOn() {
    return mProducedOn;
  }

  @Override
  public Date getConsumedOn() {
    return mConsumedOn;
  }

  @Override
  public MutableNotification edit() throws Exception {
    return new PersistentNotification(this);
  }

  @Override
  public Integer getId() {
    return mId;
  }

  @Override
  public String getLastModified() {
    return mLastModified;
  }

  @Override
  public void delete() throws Exception {
    sNotificationManager.delete(this);
  }

  @Override
  public void setLastModified(String pLastModified) {
    mLastModified = pLastModified;
  }
}
