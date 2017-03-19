package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Employee;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.mutable.MutableUgAdmissionLog;
import org.ums.manager.EmployeeManager;
import org.ums.manager.SemesterManager;
import org.ums.manager.UgAdmissionLogManager;

/**
 * Created by Monjur-E-Morshed on 01-Jan-17.
 */
public class PersistentUgAdmissionLog implements MutableUgAdmissionLog {

  private static SemesterManager sSemesterManager;
  private static UgAdmissionLogManager sUgAdmissionLogManager;
  private static EmployeeManager sEmployeeManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sSemesterManager = applicationContext.getBean("semesterManager", SemesterManager.class);
    sEmployeeManager = applicationContext.getBean("employeeManager", EmployeeManager.class);
    sUgAdmissionLogManager = applicationContext.getBean("ugAdmissionLogManager", UgAdmissionLogManager.class);
  }

  private int mId;
  private String mReceiptId;
  private int mSemesterId;
  private Semester mSemester;
  private String mLogText;
  private String mActorId;
  private Employee mActor;
  private String mInsertedOn;

  public PersistentUgAdmissionLog() {

  }

  public PersistentUgAdmissionLog(final PersistentUgAdmissionLog pPersistentUgAdmissionLog) {
    mId = pPersistentUgAdmissionLog.getId();
    mReceiptId = pPersistentUgAdmissionLog.getReceiptId();
    mSemesterId = pPersistentUgAdmissionLog.getSemesterId();
    mSemester = pPersistentUgAdmissionLog.getSemester();
    mLogText = pPersistentUgAdmissionLog.getLogText();
    mActorId = pPersistentUgAdmissionLog.mActorId;
    mActor = pPersistentUgAdmissionLog.getActor();
    mInsertedOn = pPersistentUgAdmissionLog.getInsertionDate();
  }

  @Override
  public void commit(boolean update) {
    if(update) {
      sUgAdmissionLogManager.update(this);
    }
    else {
      sUgAdmissionLogManager.create(this);
    }
  }

  @Override
  public void setActorId(String pActorId) {
    mActorId = pActorId;
  }

  @Override
  public void setActor(Employee pEmployee) {
    mActor = pEmployee;
  }

  @Override
  public MutableUgAdmissionLog edit() {
    return new PersistentUgAdmissionLog(this);
  }

  @Override
  public String getLastModified() {
    return mInsertedOn;
  }

  @Override
  public Integer getId() {
    return mId;
  }

  @Override
  public void setId(Integer pId) {
    mId = pId;
  }

  @Override
  public void setLastModified(String pLastModified) {

  }

  @Override
  public void delete() {
    sUgAdmissionLogManager.delete(this);
  }

  @Override
  public void setReceiptId(String pReceiptId) {
    mReceiptId = pReceiptId;
  }

  @Override
  public String getReceiptId() {
    return mReceiptId;
  }

  @Override
  public void setSemesterId(int pSemesterId) {
    mSemesterId = pSemesterId;
  }

  @Override
  public int getSemesterId() {
    return mSemesterId;
  }

  @Override
  public Semester getSemester() {
    return mSemester == null ? sSemesterManager.get(mSemesterId) : sSemesterManager.validate(mSemester);
  }

  @Override
  public void setSemester(Semester pSemester) {
    mSemester = pSemester;
  }

  @Override
  public String getLogText() {
    return mLogText;
  }

  @Override
  public String getActorId() {
    return mActorId;
  }

  @Override
  public void setLogText(String pLogText) {
    mLogText = pLogText;
  }

  @Override
  public Employee getActor() {
    return mActor == null ? sEmployeeManager.get(mActorId) : sEmployeeManager.validate(mActor);
  }

  @Override
  public void setInsertionDate(String pInsertionDate) {
    mInsertedOn = pInsertionDate;
  }

  @Override
  public String getInsertionDate() {
    return mInsertedOn;
  }
}
