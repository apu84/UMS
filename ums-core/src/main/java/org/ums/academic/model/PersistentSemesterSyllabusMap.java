package org.ums.academic.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.MutableSemesterSyllabusMap;
import org.ums.domain.model.readOnly.*;
import org.ums.manager.ContentManager;

/**
 * Created by Ifti on 08-Jan-16.
 */
public class PersistentSemesterSyllabusMap implements MutableSemesterSyllabusMap {

  private static ContentManager<SemesterSyllabusMap, MutableSemesterSyllabusMap, Integer> sSemesterSyllabusMapManager;
  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sSemesterSyllabusMapManager = (ContentManager<SemesterSyllabusMap, MutableSemesterSyllabusMap, Integer>)applicationContext.getBean("semesterSyllabusMapManager");
  }

  private int mId;
  private Semester mAcademicSemester;
  private Program mProgram;
  private Syllabus mSyllabus;
  private int mYear;
  private int mSemester;

  public PersistentSemesterSyllabusMap() {

  }

  public PersistentSemesterSyllabusMap(final PersistentSemesterSyllabusMap pOriginal) throws Exception {
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
  public void delete() throws Exception {
    sSemesterSyllabusMapManager.delete(this);
  }

  public void commit(final boolean update) throws Exception {
    if (update) {
      sSemesterSyllabusMapManager.update(this);
    } else {
      sSemesterSyllabusMapManager.create(this);
    }
  }

  public MutableSemesterSyllabusMap edit() throws Exception {
    return new PersistentSemesterSyllabusMap(this);
  }
}
