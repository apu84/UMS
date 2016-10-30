package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.TaskStatus;
import org.ums.domain.model.mutable.MutableTaskStatus;
import org.ums.manager.TaskStatusManager;

import java.util.Date;

public class PersistentTaskStatus implements MutableTaskStatus {
  private static TaskStatusManager sTaskStatusManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sTaskStatusManager = applicationContext.getBean("taskStatusManager", TaskStatusManager.class);
  }

  private String mId;
  private String mTaskName;
  private TaskStatus.Status mStatus;
  private String mLastModified;
  private String mProgressDescription;
  private Date mTaskCompletionDate;

  public PersistentTaskStatus() {}

  private PersistentTaskStatus(final PersistentTaskStatus pPersistentTaskStatus) throws Exception {
    setId(pPersistentTaskStatus.getId());
    setStatus(pPersistentTaskStatus.getStatus());
    setTaskName(pPersistentTaskStatus.getTaskName());
    setLastModified(pPersistentTaskStatus.getLastModified());
  }

  @Override
  public void commit(boolean update) throws Exception {
    if(update) {
      sTaskStatusManager.update(this);
    }
    else {
      sTaskStatusManager.create(this);
    }
  }

  @Override
  public MutableTaskStatus edit() throws Exception {
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
  public void delete() throws Exception {
    sTaskStatusManager.delete(this);
  }

  @Override
  public void setTaskName(String pTaskName) {
    mTaskName = pTaskName;
  }

  @Override
  public void setStatus(Status pStatus) {
    mStatus = pStatus;
  }

  @Override
  public String getTaskName() throws Exception {
    return mTaskName;
  }

  @Override
  public Status getStatus() throws Exception {
    return mStatus;
  }

  @Override
  public void setProgressDescription(String pProgressDescription) {
    mProgressDescription = pProgressDescription;
  }

  @Override
  public String getProgressDescription() throws Exception {
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
}
