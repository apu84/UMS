package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.MutableApplicationTesSetQuestions;
import org.ums.manager.ApplicationTesSetQuestionManager;
import org.ums.manager.CourseManager;
import org.ums.manager.SemesterManager;
import org.ums.manager.StudentManager;

/**
 * Created by Monjur-E-Morshed on 4/26/2018.
 */
public class PersistentApplicationTesSetQuestion implements MutableApplicationTesSetQuestions {
  private static StudentManager sStudentManager;
  private static SemesterManager sSemesterManager;
  private static CourseManager sCourseManager;
  private static ApplicationTesSetQuestionManager sApplicationTesSetQuestionManager;
  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sStudentManager = applicationContext.getBean("studentManager", StudentManager.class);
    sSemesterManager = applicationContext.getBean("semesterManager", SemesterManager.class);
    sCourseManager = applicationContext.getBean("courseManager", CourseManager.class);
    sApplicationTesSetQuestionManager =
        applicationContext.getBean("applicationTesSetQuestionManager", ApplicationTesSetQuestionManager.class);
  }
  private Long mId;
  private String mApplicationDate;
  private Long mQuestionID;
  private Integer mSemesterid;

  public PersistentApplicationTesSetQuestion() {

  }

  public PersistentApplicationTesSetQuestion(
      final PersistentApplicationTesSetQuestion persistentApplicationTesSetQuestion) {
    mId = persistentApplicationTesSetQuestion.getId();
    mApplicationDate = persistentApplicationTesSetQuestion.getApplicationDate();
    mQuestionID = persistentApplicationTesSetQuestion.getQuestionId();
    mSemesterid = persistentApplicationTesSetQuestion.getSemesterId();

  }

  @Override
  public void setApplicationDate(String pApplicationDate) {
    mApplicationDate = pApplicationDate;
  }

  @Override
  public void setQuestionId(Long pQuestionId) {
    mQuestionID = pQuestionId;
  }

  @Override
  public void setSemesterId(Integer pSemesterId) {
    mSemesterid = pSemesterId;
  }

  @Override
  public MutableApplicationTesSetQuestions edit() {
    return new PersistentApplicationTesSetQuestion(this);
  }

  @Override
  public Long create() {
    return sApplicationTesSetQuestionManager.create(this);
  }

  @Override
  public void update() {
    sApplicationTesSetQuestionManager.update(this);
  }

  @Override
  public void delete() {
    sApplicationTesSetQuestionManager.delete(this);
  }

  @Override
  public void setId(Long pId) {
    mId = pId;
  }

  @Override
  public String getApplicationDate() {
    return mApplicationDate;
  }

  @Override
  public Long getQuestionId() {
    return mQuestionID;
  }

  @Override
  public Integer getSemesterId() {
    return mSemesterid;
  }

  @Override
  public Long getId() {
    return mId;
  }

  @Override
  public String getLastModified() {
    return null;
  }

  @Override
  public void setLastModified(String pLastModified) {

  }
}
