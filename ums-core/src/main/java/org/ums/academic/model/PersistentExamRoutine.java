package org.ums.academic.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.dto.ExamRoutineDto;
import org.ums.domain.model.mutable.MutableExamRoutine;
import org.ums.domain.model.readOnly.ExamRoutine;
import org.ums.manager.ContentManager;

import java.util.List;

public class PersistentExamRoutine implements MutableExamRoutine {

  private static ContentManager<ExamRoutine, MutableExamRoutine, Object> sExamRoutineManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sExamRoutineManager = (ContentManager<ExamRoutine, MutableExamRoutine, Object>) applicationContext.getBean("examRoutineManager");
  }


  private List<ExamRoutineDto> mExamRoutine;
  private String mInsertType;
  private int mSemesterId;
  private String mSemesterName;
  private int mExamTypeId;
  private String mExamTypeName;


  public PersistentExamRoutine() {
  }

  public PersistentExamRoutine(final MutableExamRoutine pOriginal) throws Exception {
    mExamRoutine = pOriginal.getRoutine();
  }

  @Override
  public void setRoutine(List<ExamRoutineDto> pRoutineList) {
    mExamRoutine = pRoutineList;
  }

  @Override
  public List<ExamRoutineDto> getRoutine() {
    return mExamRoutine;
  }

  public void save() throws Exception {

    sExamRoutineManager.create(this);

  }

  @Override
  public void delete() throws Exception {
    sExamRoutineManager.delete(this);

  }


  @Override
  public void commit(boolean update) throws Exception {
  }

  @Override
  public MutableExamRoutine edit() throws Exception {
    return null;
  }

  @Override
  public String getInsertType() {
    return mInsertType;
  }

  @Override
  public void setInsertType(String mInsertType) {
    this.mInsertType = mInsertType;
  }

  @Override

  public int getSemesterId() {
    return mSemesterId;
  }

  @Override
  public void setSemesterId(int mSemesterId) {
    this.mSemesterId = mSemesterId;
  }

  @Override
  public String getSemesterName() {
    return mSemesterName;
  }

  @Override
  public void setSemesterName(String mSemesterName) {
    this.mSemesterName = mSemesterName;
  }

  @Override
  public int getExamTypeId() {
    return mExamTypeId;
  }

  @Override
  public void setExamTypeId(int mExamTypeId) {
    this.mExamTypeId = mExamTypeId;
  }

  @Override
  public String getExamTypeName() {
    return mExamTypeName;
  }

  @Override
  public void setExamTypeName(String mExamTypeName) {
    this.mExamTypeName = mExamTypeName;
  }
}
