package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Employee;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.SemesterWithdrawal;
import org.ums.domain.model.immutable.Student;
import org.ums.domain.model.mutable.MutableSemesterWithdrawalLog;
import org.ums.manager.EmployeeManager;
import org.ums.manager.SemesterManager;
import org.ums.manager.SemesterWithDrawalManager;
import org.ums.manager.SemesterWithdrawalLogManager;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PersistentSemesterWithdrawalLog implements MutableSemesterWithdrawalLog {

  private static SemesterWithDrawalManager sSemesterWithdrawalManager;
  private static SemesterWithdrawalLogManager sSemesterWithdrawalLogManager;
  private static EmployeeManager sEmployeeManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sSemesterWithdrawalManager =
        applicationContext.getBean("semesterWithdrawalManager", SemesterWithDrawalManager.class);
    sEmployeeManager = applicationContext.getBean("employeeManager", EmployeeManager.class);
    sSemesterWithdrawalLogManager =
        applicationContext.getBean("semesterWithdrawalLogManager",
            SemesterWithdrawalLogManager.class);
  }

  private Long mId;
  private SemesterWithdrawal mSemesterWithdrawal;
  private Long mSemesterWithdrawalId;
  private Employee mEmployee;
  private String mEmployeeId;
  private String mEventDateTime;
  private int mAction;
  private String mLastModified;

  public PersistentSemesterWithdrawalLog() {

  }

  public PersistentSemesterWithdrawalLog(
      final PersistentSemesterWithdrawalLog pPersistentSemesterWithdrawalLog) {
    mId = pPersistentSemesterWithdrawalLog.getId();

    mSemesterWithdrawal = pPersistentSemesterWithdrawalLog.getSemesterWithdrawal();
    mSemesterWithdrawalId = pPersistentSemesterWithdrawalLog.getSemesterWithdrawalId();
    mEventDateTime = pPersistentSemesterWithdrawalLog.getEventDateTime();
    mAction = pPersistentSemesterWithdrawalLog.getAction();
    mLastModified = pPersistentSemesterWithdrawalLog.getLastModified();
  }

  @Override
  public void setEventDate(final String mDate) {
    /*
     * DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm"); Date date = new Date();
     * System.out.println(dateFormat.format(date)); //2014/08/06 15:59:48 mEventDateTime =
     * dateFormat.format(date);
     */
    mEventDateTime = mDate;
  }

  public String getEmployeeId() {
    return mEmployeeId;
  }

  public void setEmployeeId(String pEmployeeId) {
    mEmployeeId = pEmployeeId;
  }

  public Long getSemesterWithdrawalId() {
    return mSemesterWithdrawalId;
  }

  public void setSemesterWithdrawalId(Long pSemesterWithdrawalId) {
    mSemesterWithdrawalId = pSemesterWithdrawalId;
  }

  @Override
  public void setSemesterWithdrawal(SemesterWithdrawal pSemesterWithdrawal) {
    mSemesterWithdrawal = pSemesterWithdrawal;
  }

  @Override
  public SemesterWithdrawal getSemesterWithdrawal() {
    return mSemesterWithdrawal == null ? sSemesterWithdrawalManager.get(mSemesterWithdrawalId)
        : sSemesterWithdrawalManager.validate(mSemesterWithdrawal);
  }

  @Override
  public void setAction(int pAction) {
    mAction = pAction;
  }

  @Override
  public Long create() {
    return sSemesterWithdrawalLogManager.create(this);
  }

  @Override
  public void update() {
    sSemesterWithdrawalLogManager.update(this);
  }

  @Override
  public void delete() {
    sSemesterWithdrawalLogManager.delete(this);
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
  public String getEventDateTime() {
    /*
     * DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm"); Date date = new Date();
     * System.out.println(dateFormat.format(date)); //2014/08/06 15:59:48 mEventDateTime =
     * dateFormat.format(date);
     */
    return mEventDateTime;
  }

  @Override
  public int getAction() {
    return mAction;
  }

  @Override
  public MutableSemesterWithdrawalLog edit() {
    return new PersistentSemesterWithdrawalLog(this);
  }

  @Override
  public Long getId() {
    return mId;
  }

  @Override
  public String getLastModified() {
    return mLastModified;
  }
}
