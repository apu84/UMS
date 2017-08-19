package org.ums.persistent.model.meeting;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.meeting.MutableAgendaResolution;
import org.ums.manager.meeting.AgendaResolutionManager;

public class PersistentAgendaResolution implements MutableAgendaResolution {

  private static AgendaResolutionManager sAgendaResolutionManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sAgendaResolutionManager = applicationContext.getBean("agendaResolutionManager", AgendaResolutionManager.class);
  }

  private Long mId;
  private String mAgendaNo;
  private String mAgenda;
  private String mAgendaEditor;
  private String mResolution;
  private String mResolutionEditor;
  private Long mScheduleId;
  private String mLastModified;

  public PersistentAgendaResolution() {}

  public PersistentAgendaResolution(PersistentAgendaResolution pPersistentAgendaResolution) {
    mId = pPersistentAgendaResolution.getId();
    mAgendaNo = pPersistentAgendaResolution.getAgendaNo();
    mAgenda = pPersistentAgendaResolution.getAgenda();
    mAgendaEditor = pPersistentAgendaResolution.getAgendaEditor();
    mResolution = pPersistentAgendaResolution.getResolution();
    mResolutionEditor = pPersistentAgendaResolution.getResolutionEditor();
    mScheduleId = pPersistentAgendaResolution.getScheduleId();
    mLastModified = pPersistentAgendaResolution.getLastModified();
  }

  @Override
  public void setAgendaNo(String pAgendaNo) {
    mAgendaNo = pAgendaNo;
  }

  @Override
  public void setAgenda(String pAgenda) {
    mAgenda = pAgenda;
  }

  @Override
  public void setAgendaEditor(String pAgendaEditor) {
    mAgendaEditor = pAgendaEditor;
  }

  @Override
  public void setResolution(String pResolution) {
    mResolution = pResolution;
  }

  @Override
  public void setResolutionEditor(String pResolutionEditor) {
    mResolutionEditor = pResolutionEditor;
  }

  @Override
  public void setScheduleId(Long pScheduleId) {
    mScheduleId = pScheduleId;
  }

  @Override
  public MutableAgendaResolution edit() {
    return new PersistentAgendaResolution(this);
  }

  @Override
  public Long create() {
    return sAgendaResolutionManager.create(this);
  }

  @Override
  public void update() {
    sAgendaResolutionManager.update(this);
  }

  @Override
  public void delete() {
    sAgendaResolutionManager.delete(this);
  }

  @Override
  public void setId(Long pId) {
    mId = pId;
  }

  @Override
  public String getAgendaNo() {
    return mAgendaNo;
  }

  @Override
  public String getAgenda() {
    return mAgenda;
  }

  @Override
  public String getAgendaEditor() {
    return mAgendaEditor;
  }

  @Override
  public String getResolution() {
    return mResolution;
  }

  @Override
  public String getResolutionEditor() {
    return mResolutionEditor;
  }

  @Override
  public Long getScheduleId() {
    return mScheduleId;
  }

  @Override
  public Long getId() {
    return mId;
  }

  @Override
  public String getLastModified() {
    return mLastModified;
  }

  @Override
  public void setLastModified(String pLastModified) {
    mLastModified = pLastModified;
  }
}
