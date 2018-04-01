package org.ums.persistent.model;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.TaskStatus;
import org.ums.domain.model.mutable.MutableTaskStatus;
import org.ums.formatter.DateFormat;
import org.ums.manager.TaskStatusManager;

public class PersistentTaskStatus implements MutableTaskStatus {
  private static TaskStatusManager sTaskStatusManager;
  private static DateFormat sDateFormat;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sTaskStatusManager = applicationContext.getBean("taskStatusManager", TaskStatusManager.class);
    sDateFormat = applicationContext.getBean("genericDateTimeFormat12", DateFormat.class);
  }

  private String mId;
  private TaskStatus.Status mStatus;
  private String mLastModified;
  private String mProgressDescription;
  private Date mTaskCompletionDate;

  public PersistentTaskStatus() {}

  private PersistentTaskStatus(final PersistentTaskStatus pPersistentTaskStatus) {
    setId(pPersistentTaskStatus.getId());
    setStatus(pPersistentTaskStatus.getStatus());
    setLastModified(pPersistentTaskStatus.getLastModified());
  }

  @Override
  public String create() {
    return sTaskStatusManager.create(this);
  }

  @Override
  public void update() {
    sTaskStatusManager.update(this);
  }

  @Override
  public MutableTaskStatus edit() {
    return new PersistentTaskStatus(this);
  }

  @Override
  public String getLastModified() {
    return mLastModified;
  }

  @Override
  public String getId() {
    return mId;
  }

  @Override
  public void setId(String pId) {
    mId = pId;
  }

  @Override
  public void setLastModified(String pLastModified) {
    mLastModified = pLastModified;
  }

  @Override
  public void delete() {
    sTaskStatusManager.delete(this);
  }

  @Override
  public void setStatus(Status pStatus) {
    mStatus = pStatus;
  }

  @Override
  public Status getStatus() {
    return mStatus;
  }

  @Override
  public void setProgressDescription(String pProgressDescription) {
    mProgressDescription = pProgressDescription;
  }

  @Override
  public String getProgressDescription() {
    return mProgressDescription;
  }

  @Override
  public void setTaskCompletionDate(Date pDate) {
    mTaskCompletionDate = pDate;
  }

  @Override
  public Date getTaskCompletionDate() {
    return mTaskCompletionDate;
  }

  @Override
  public void setTaskCompletionDateString(String pCompleteDateString) {}

  @Override
  public String getTaskCompletionDateString() {
    return sDateFormat.format(mTaskCompletionDate);
  }
}
