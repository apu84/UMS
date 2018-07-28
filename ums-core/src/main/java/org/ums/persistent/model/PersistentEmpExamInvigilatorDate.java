package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.MutableEmpExamInvigilatorDate;
import org.ums.manager.EmpExamInvigilatorDateManager;

/**
 * Created by Monjur-E-Morshed on 7/27/2018.
 */
public class PersistentEmpExamInvigilatorDate implements MutableEmpExamInvigilatorDate {
  private static EmpExamInvigilatorDateManager sEmpExamInvigilatorDateManager;
  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sEmpExamInvigilatorDateManager =
        applicationContext.getBean("empExamInvigilatorDateManager", EmpExamInvigilatorDateManager.class);
  }
  private Long mId;
  private String mExamDate;
  private Long mAttendantId;

  public PersistentEmpExamInvigilatorDate() {

  }

  public PersistentEmpExamInvigilatorDate(PersistentEmpExamInvigilatorDate pPersistentEmpExamInvigilatorDate) {
    mId = pPersistentEmpExamInvigilatorDate.getId();
    mExamDate = pPersistentEmpExamInvigilatorDate.getExamDate();
    mAttendantId = pPersistentEmpExamInvigilatorDate.getAttendantId();
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
  public MutableEmpExamInvigilatorDate edit() {
    return new PersistentEmpExamInvigilatorDate(this);
  }

  @Override
  public Long create() {
    return sEmpExamInvigilatorDateManager.create(this);
  }

  @Override
  public void update() {
    sEmpExamInvigilatorDateManager.update(this);
  }

  @Override
  public void delete() {
    sEmpExamInvigilatorDateManager.delete(this);
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
