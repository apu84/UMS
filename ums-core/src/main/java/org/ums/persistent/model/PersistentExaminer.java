package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Examiner;
import org.ums.domain.model.immutable.Teacher;
import org.ums.domain.model.mutable.MutableExaminer;
import org.ums.manager.AssignedTeacherManager;

public class PersistentExaminer extends AbstractAssignedTeacher implements MutableExaminer {
  private static AssignedTeacherManager<Examiner, MutableExaminer, Long> sExaminerManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sExaminerManager = applicationContext.getBean("examinerManager", AssignedTeacherManager.class);
  }

  private String mPreparerId;
  private Teacher mPreparer;
  private String mScrutinizerId;
  private Teacher mScrutinizer;

  public PersistentExaminer() {}

  public PersistentExaminer(final PersistentExaminer pPersistentExaminer) {
    this.mId = pPersistentExaminer.getId();
    this.mSemester = pPersistentExaminer.getSemester();
    this.mCourse = pPersistentExaminer.getCourse();
    this.mLastModified = pPersistentExaminer.getLastModified();
  }

  @Override
  public void setPreparerId(String pTeacherId) {
    mPreparerId = pTeacherId;
  }

  @Override
  public void setPreparer(Teacher pTeacher) {
    mPreparer = pTeacher;
  }

  @Override
  public void setScrutinizer(Teacher pTeacherId) {
    mScrutinizer = pTeacherId;
  }

  @Override
  public void setScrutinizerId(String pTeacherId) {
    mScrutinizerId = pTeacherId;
  }

  @Override
  public String getPreparerId() {
    return mPreparerId;
  }

  @Override
  public Teacher getPreparer() {
    return mPreparer == null ? sTeacherManager.get(mPreparerId) : sTeacherManager
        .validate(mPreparer);
  }

  @Override
  public String getScrutinizerId() {
    return mScrutinizerId;
  }

  @Override
  public Teacher getScrutinizer() {
    return mScrutinizer == null ? sTeacherManager.get(mScrutinizerId) : sTeacherManager
        .validate(mScrutinizer);
  }

  @Override
  public MutableExaminer edit() {
    return new PersistentExaminer(this);
  }

  @Override
  public void delete() {
    sExaminerManager.delete(this);
  }

  @Override
  public Long create() {
    return sExaminerManager.create(this);
  }

  @Override
  public void update() {
    sExaminerManager.update(this);
  }

}
