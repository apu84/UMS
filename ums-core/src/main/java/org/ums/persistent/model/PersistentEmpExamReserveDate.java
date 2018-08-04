package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.MutableEmpExamReserveDate;
import org.ums.manager.EmpExamReserveDateManager;

/**
 * Created by Monjur-E-Morshed on 7/27/2018.
 */
public class PersistentEmpExamReserveDate implements MutableEmpExamReserveDate {
  private static EmpExamReserveDateManager sEmpExamReserveDateManager;
  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sEmpExamReserveDateManager =
        applicationContext.getBean("empExamReserveDateManager", EmpExamReserveDateManager.class);
  }
  private Long mId;
  private String mExamDate;
  private Long mAttendantId;

  public PersistentEmpExamReserveDate() {

  }

  public PersistentEmpExamReserveDate(PersistentEmpExamReserveDate pPersistentEmpExamReserveDate) {
    mId = pPersistentEmpExamReserveDate.getId();
    mExamDate = pPersistentEmpExamReserveDate.getExamDate();
    mAttendantId = pPersistentEmpExamReserveDate.getAttendantId();
  }

  @Override
  public void setId(Long pId) {
    mId = pId;
  }

  @Override
  public void setExamDate(String pExamDate) {
    mExamDate = pExamDate;
  }

  @Override
  public void setAttendantId(Long pAttendantId) {
    mAttendantId = pAttendantId;
  }

  @Override
  public MutableEmpExamReserveDate edit() {
    return new PersistentEmpExamReserveDate(this);
  }

  @Override
  public Long create() {
    return sEmpExamReserveDateManager.create(this);
  }

  @Override
  public void update() {
    sEmpExamReserveDateManager.update(this);
  }

  @Override
  public void delete() {
    sEmpExamReserveDateManager.delete(this);
  }

  @Override
  public Long getId() {
    return mId;
  }

  @Override
  public String getExamDate() {
    return mExamDate;
  }

  @Override
  public Long getAttendantId() {
    return mAttendantId;
  }

  @Override
  public String getLastModified() {
    return null;
  }

  @Override
  public void setLastModified(String pLastModified) {

  }
}
