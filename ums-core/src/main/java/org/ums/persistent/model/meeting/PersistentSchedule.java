package org.ums.persistent.model.meeting;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.meeting.MutableSchedule;
import org.ums.enums.meeting.MeetingType;
import org.ums.manager.meeting.ScheduleManager;
import org.ums.persistent.model.PersistentTaskStatus;

import java.sql.Time;
import java.util.Date;

public class PersistentSchedule implements MutableSchedule {

  private static ScheduleManager sScheduleManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sScheduleManager = applicationContext.getBean("scheduleManager", ScheduleManager.class);
  }

  private int mId;
  private MeetingType mMeetingType;
  private int mMeetingTypeId;
  private int mMeetingNo;
  private String mMeetingRefNo;
  private Date mMeetingDate;
  private Time mMeetingTime;
  private String mMeetingRoomNo;
  private String mLastModified;

  public PersistentSchedule() {}

  public PersistentSchedule(PersistentSchedule pPersistentSchedule) {
    mId = pPersistentSchedule.getId();
    mMeetingType = pPersistentSchedule.getMeetingType();
    mMeetingTypeId = pPersistentSchedule.getMeetingTypeId();
    mMeetingNo = pPersistentSchedule.getMeetingNo();
    mMeetingRefNo = pPersistentSchedule.getMeetingRefNo();
    mMeetingDate = pPersistentSchedule.getMeetingDate();
    mMeetingTime = pPersistentSchedule.getMeetingTime();
    mMeetingRoomNo = pPersistentSchedule.getMeetingRoomNo();
    mLastModified = pPersistentSchedule.getLastModified();
  }

  @Override
  public MutableSchedule edit() {
    return new PersistentSchedule(this);
  }

  @Override
  public Integer create() {
    return sScheduleManager.create(this);
  }

  @Override
  public void update() {
    sScheduleManager.update(this);
  }

  @Override
  public void delete() {
    sScheduleManager.delete(this);
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
  public void setId(Integer pId) {
    mId = pId;
  }

  @Override
  public void setLastModified(String pLastModified) {
    mLastModified = pLastModified;
  }

  @Override
  public void setMeetingType(MeetingType pMeetingType) {
    mMeetingType = pMeetingType;
  }

  @Override
  public void setMeetingTypeId(int pMeetingTypeId) {
    mMeetingTypeId = pMeetingTypeId;
  }

  @Override
  public void setMeetingNo(int pMeetingNo) {
    mMeetingNo = pMeetingNo;
  }

  @Override
  public void setMeetingRefNo(String pMeetingRefNo) {
    mMeetingRefNo = pMeetingRefNo;
  }

  @Override
  public void setMeetingDate(Date pMeetingDate) {
    mMeetingDate = pMeetingDate;
  }

  @Override
  public void setMeetingTime(Time pMeetingTime) {
    mMeetingTime = pMeetingTime;
  }

  @Override
  public void setMeetingRoomNo(String pMeetingRoomNo) {
    mMeetingRoomNo = pMeetingRoomNo;
  }

  @Override
  public MeetingType getMeetingType() {
    return mMeetingType;
  }

  @Override
  public int getMeetingTypeId() {
    return mMeetingTypeId;
  }

  @Override
  public int getMeetingNo() {
    return mMeetingNo;
  }

  @Override
  public String getMeetingRefNo() {
    return mMeetingRefNo;
  }

  @Override
  public Date getMeetingDate() {
    return mMeetingDate;
  }

  @Override
  public Time getMeetingTime() {
    return mMeetingTime;
  }

  @Override
  public String getMeetingRoomNo() {
    return mMeetingRoomNo;
  }
}
