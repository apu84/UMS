package org.ums.persistent.model.optCourse;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.optCourse.MutableOptStudentCourseSelection;
import org.ums.manager.optCourse.OptStudentCourseSelectionManager;

/**
 * Created by Monjur-E-Morshed on 9/27/2018.
 */
public class PersistentOptStudentCourseSelection implements MutableOptStudentCourseSelection {
  private static OptStudentCourseSelectionManager sOptStudentCourseSelectionManager;
  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sOptStudentCourseSelectionManager =
        applicationContext.getBean("optStudentCourseSelectionManager", OptStudentCourseSelectionManager.class);
  }
  private Long mId;
  private Long mGroupId;
  private Long mSubGroupId;
  private Integer mChoice;
  private String mStudentId;
  private Integer mSemesterId;
  private Integer mProgramId;
  private Integer mYear;
  private Integer mSemester;
  private Integer mDepartmentId;

  public PersistentOptStudentCourseSelection() {

  }

  public PersistentOptStudentCourseSelection(PersistentOptStudentCourseSelection pPersistentOptStudentCourseSelection) {
    mId = pPersistentOptStudentCourseSelection.getId();
    mGroupId = pPersistentOptStudentCourseSelection.getGroupId();
    mSubGroupId = pPersistentOptStudentCourseSelection.getSubGroupId();
    mChoice = pPersistentOptStudentCourseSelection.getStudentChoice();
    mStudentId = pPersistentOptStudentCourseSelection.getStudentId();
    mSemesterId = pPersistentOptStudentCourseSelection.getSemesterId();
    mProgramId = pPersistentOptStudentCourseSelection.getProgramId();
    mYear = pPersistentOptStudentCourseSelection.getYear();
    mSemester = pPersistentOptStudentCourseSelection.getSemester();
    mDepartmentId = pPersistentOptStudentCourseSelection.getDepartmentId();
  }

  @Override
  public void setId(Long pId) {
    mId = pId;

  }

  @Override
  public void setGroupId(Long pGroupId) {
    mGroupId = pGroupId;
  }

  @Override
  public void setSubGroupId(Long pSubGroupId) {
    mSubGroupId = pSubGroupId;
  }

  @Override
  public void setStudentId(String pStudentId) {
    mStudentId = pStudentId;
  }

  @Override
  public void setStudentChoice(Integer pStudentChoice) {
    mChoice = pStudentChoice;
  }

  @Override
  public void setProgramId(Integer pProgramId) {
    mProgramId = pProgramId;
  }

  @Override
  public void setSemesterId(Integer pSemesterId) {
    mSemesterId = pSemesterId;
  }

  @Override
  public void setYear(Integer pYear) {
    mYear = pYear;

  }

  @Override
  public void setSemester(Integer pSemester) {
    mSemester = pSemester;
  }

  @Override
  public void setDepartmentId(Integer pDepartmentId) {
    mDepartmentId = pDepartmentId;
  }

  @Override
  public Long create() {
    return sOptStudentCourseSelectionManager.create(this);
  }

  @Override
  public void update() {
    sOptStudentCourseSelectionManager.update(this);
  }

  @Override
  public void delete() {
    sOptStudentCourseSelectionManager.delete(this);

  }

  @Override
  public Long getId() {
    return mId;
  }

  @Override
  public Long getGroupId() {
    return mGroupId;
  }

  @Override
  public Long getSubGroupId() {
    return mSubGroupId;
  }

  @Override
  public String getStudentId() {
    return mStudentId;
  }

  @Override
  public Integer getStudentChoice() {
    return mChoice;
  }

  @Override
  public Integer getProgramId() {
    return mProgramId;
  }

  @Override
  public Integer getSemesterId() {
    return mSemesterId;
  }

  @Override
  public Integer getYear() {
    return mYear;
  }

  @Override
  public Integer getSemester() {
    return mSemester;
  }

  @Override
  public Integer getDepartmentId() {
    return mDepartmentId;
  }

  @Override
  public MutableOptStudentCourseSelection edit() {
    return null;
  }

  @Override
  public String getLastModified() {
    return null;
  }

  @Override
  public void setLastModified(String pLastModified) {

  }
}
