package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.MutableApplicationTesQuestions;
import org.ums.manager.ApplicationTesQuestionManager;
import org.ums.manager.CourseManager;
import org.ums.manager.SemesterManager;
import org.ums.manager.StudentManager;

/**
 * Created by Monjur-E-Morshed on 4/16/2018.
 */
public class PersistentApplicationTesQuestion implements MutableApplicationTesQuestions {
  private static StudentManager sStudentManager;
  private static SemesterManager sSemesterManager;
  private static CourseManager sCourseManager;
  private static ApplicationTesQuestionManager sApplicationTesQuestionManager;
  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sStudentManager = applicationContext.getBean("studentManager", StudentManager.class);
    sSemesterManager = applicationContext.getBean("semesterManager", SemesterManager.class);
    sCourseManager = applicationContext.getBean("courseManager", CourseManager.class);
    sApplicationTesQuestionManager =
        applicationContext.getBean("applicationTESQuestionManager", ApplicationTesQuestionManager.class);
  }
  private Long mId;
  private String mApplicationDate;
  private Integer mQuestionID;
  private String mQuestionDetails;
  private Integer mObservationType;
  private Integer mSemesterId;

  public PersistentApplicationTesQuestion() {

  }

  public PersistentApplicationTesQuestion(final PersistentApplicationTesQuestion persistentApplicationTesQuestion) {
    mId = persistentApplicationTesQuestion.getId();
    mApplicationDate = persistentApplicationTesQuestion.getApplicationDate();
    mQuestionID = persistentApplicationTesQuestion.getQuestionId();
    mQuestionDetails = persistentApplicationTesQuestion.getQuestionDetails();
    mObservationType = persistentApplicationTesQuestion.getObservationType();
    mSemesterId = persistentApplicationTesQuestion.getSemester();
  }

  @Override
  public void setQuestionId(Integer pQuestionId) {
    mQuestionID = pQuestionId;
  }

  @Override
  public void setQuestionDetails(String pQuestionDetails) {
    mQuestionDetails = pQuestionDetails;
  }

  @Override
  public void setObservationType(Integer pObservationType) {
    mObservationType = pObservationType;
  }

  @Override
  public void setSemester(Integer pSemesterId) {
    mSemesterId = pSemesterId;
  }

  @Override
  public Integer getQuestionId() {
    return mQuestionID;
  }

  @Override
  public String getQuestionDetails() {
    return mQuestionDetails;
  }

  @Override
  public Integer getObservationType() {
    return mObservationType;
  }

  @Override
  public Integer getSemester() {
    return mSemesterId;
  }

  @Override
  public MutableApplicationTesQuestions edit() {
    return new PersistentApplicationTesQuestion(this);
  }

  @Override
  public Long create() {
    return sApplicationTesQuestionManager.create(this);
  }

  @Override
  public void update() {
    sApplicationTesQuestionManager.update(this);
  }

  @Override
  public void delete() {
    sApplicationTesQuestionManager.delete(this);
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
  public void setId(Long pId) {
    mId = pId;
  }

  @Override
  public void setLastModified(String pLastModified) {

  }

  @Override
  public void setApplicationDate(String pApplicationDate) {
    mApplicationDate = pApplicationDate;
  }

  @Override
  public String getApplicationDate() {
    return mApplicationDate;
  }
}
