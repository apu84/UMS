package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Program;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.Syllabus;
import org.ums.domain.model.mutable.MutableSemesterSyllabusMap;
import org.ums.manager.SemesterSyllabusMapManager;

/**
 * Created by Ifti on 08-Jan-16.
 */
public class PersistentSemesterSyllabusMap implements MutableSemesterSyllabusMap {

  private static SemesterSyllabusMapManager sSemesterSyllabusMapManager;
  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sSemesterSyllabusMapManager =
        applicationContext.getBean("semesterSyllabusMapManager", SemesterSyllabusMapManager.class);
  }

  private int mId;
  private Semester mAcademicSemester;
  private Program mProgram;
  private Syllabus mSyllabus;
  private int mYear;
  private int mSemester;
  private String mLastModified;

  public PersistentSemesterSyllabusMap() {

  }

  public PersistentSemesterSyllabusMap(final PersistentSemesterSyllabusMap pOriginal) {
    mId = pOriginal.getId();
    mAcademicSemester = pOriginal.getAcademicSemester();
    mProgram = pOriginal.getProgram();
    mSyllabus = pOriginal.getSyllabus();
    mYear = pOriginal.getYear();
    mSemester = pOriginal.getSemester();
  }

  public Integer getId() {
    return mId;
  }

  public void setId(final Integer mId) {
    this.mId = mId;
  }

  public Semester getAcademicSemester() {
    return mAcademicSemester;
  }

  public void setAcademicSemester(Semester mAcademicSemester) {
    this.mAcademicSemester = mAcademicSemester;
  }

  public Program getProgram() {
    return mProgram;
  }

  public void setProgram(Program mProgram) {
    this.mProgram = mProgram;
  }

  public Syllabus getSyllabus() {
    return mSyllabus;
  }

  public void setSyllabus(Syllabus mSyllabus) {
    this.mSyllabus = mSyllabus;
  }

  public int getYear() {
    return mYear;
  }

  public void setYear(int mYear) {
    this.mYear = mYear;
  }

  public int getSemester() {
    return mSemester;
  }

  public void setSemester(int mSemester) {
    this.mSemester = mSemester;
  }

  public void delete() {
    sSemesterSyllabusMapManager.delete(this);
  }

  @Override
  public Integer create() {
    return sSemesterSyllabusMapManager.create(this);
  }

  @Override
  public void update() {
    sSemesterSyllabusMapManager.update(this);
  }

  public MutableSemesterSyllabusMap edit() {
    return new PersistentSemesterSyllabusMap(this);
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
