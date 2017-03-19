package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.mutable.MutableSeatPlanPublish;
import org.ums.enums.ExamType;
import org.ums.manager.SeatPlanPublishManager;
import org.ums.manager.SemesterManager;

/**
 * Created by My Pc on 8/2/2016.
 */
public class PersistentSeatPlanPublish implements MutableSeatPlanPublish {

  private static SemesterManager sSemesterManager;
  private static SeatPlanPublishManager sSeatPlanPublishManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sSemesterManager = applicationContext.getBean("semesterManager", SemesterManager.class);
    sSeatPlanPublishManager =
        applicationContext.getBean("seatPlanPublishManager", SeatPlanPublishManager.class);
  }

  private Integer mId;
  private Semester mSemester;
  private Integer mSemesterId;
  private ExamType mExamType;
  private String mExamDate;
  private Integer mIsPublished;
  private String mLastModified;

  public PersistentSeatPlanPublish() {

  }

  public PersistentSeatPlanPublish(final MutableSeatPlanPublish pMutableSeatPlanPublish) {
    mId = pMutableSeatPlanPublish.getId();
    mSemester = pMutableSeatPlanPublish.getSemester();
    mSemesterId = pMutableSeatPlanPublish.getSemesterId();
    mExamType = pMutableSeatPlanPublish.getExamType();
    mExamDate = pMutableSeatPlanPublish.getExamDate();
    mIsPublished = pMutableSeatPlanPublish.getPublishStatus();
    mLastModified = pMutableSeatPlanPublish.getLastModified();
  }

  @Override
  public void setSemester(Semester pSemester) {
    mSemester = pSemester;
  }

  @Override
  public void setSemesterId(Integer pSemesterId) {
    mSemesterId = pSemesterId;
  }

  @Override
  public void setExamType(ExamType pExamType) {
    mExamType = pExamType;
  }

  @Override
  public void setExamDate(String pExamDate) {
    mExamDate = pExamDate;
  }

  @Override
  public void setPublishStatus(Integer isPublished) {
    mIsPublished = isPublished;
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
  public Integer create() {
    return sSeatPlanPublishManager.create(this);
  }

  @Override
  public void update() {
    sSeatPlanPublishManager.update(this);
  }

  @Override
  public void delete() {
    sSeatPlanPublishManager.delete(this);
  }

  @Override
  public void setId(Integer pId) {
    mId = pId;
  }

  @Override
  public void setLastModified(String pLastModified) {
    mLastModified = pLastModified;
  }

  @Override
  public Semester getSemester() {
    return mSemester == null ? sSemesterManager.get(mSemesterId) : sSemesterManager
        .validate(mSemester);
  }

  @Override
  public Integer getSemesterId() {
    return mSemesterId;
  }

  @Override
  public ExamType getExamType() {
    return mExamType;
  }

  @Override
  public String getExamDate() {
    return mExamDate;
  }

  @Override
  public Integer getPublishStatus() {
    return mIsPublished;
  }

  @Override
  public MutableSeatPlanPublish edit() {
    return new PersistentSeatPlanPublish(this);
  }
}
