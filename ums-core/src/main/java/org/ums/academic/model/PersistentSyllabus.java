package org.ums.academic.model;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.ums.domain.model.*;
import org.ums.manager.ContentManager;
import org.ums.util.Constants;


public class PersistentSyllabus implements MutableSyllabus {
  private static ContentManager<Semester, MutableSemester, Integer> sSemesterManager;
  private static ContentManager<Program, MutableProgram, Integer> sProgramManager;
  private static ContentManager<Syllabus, MutableSyllabus, String> sSyllabusManager;
  private String mId;
  private Semester mSemester;
  private Program mProgram;
  private int mSemesterId;
  private int mProgramId;
  private String mLastModified;

  static {
    ApplicationContext applicationContext = new ClassPathXmlApplicationContext(Constants.SERVICE_CONTEXT);
    sSemesterManager = (ContentManager<Semester, MutableSemester, Integer>) applicationContext.getBean("semesterManager");
    sProgramManager = (ContentManager<Program, MutableProgram, Integer>) applicationContext.getBean("programManager");
    sSyllabusManager = (ContentManager<Syllabus, MutableSyllabus, String>) applicationContext.getBean("syllabusManager");
  }

  public PersistentSyllabus() {

  }

  public PersistentSyllabus(final PersistentSyllabus pPersistentSyllabus) throws Exception {
    mId = pPersistentSyllabus.getId();
    mSemester = pPersistentSyllabus.getSemester();
    mProgram = pPersistentSyllabus.getProgram();
    mLastModified = pPersistentSyllabus.getLastModified();
  }

  @Override
  public void commit(boolean update) throws Exception {
    if (update) {
      sSyllabusManager.update(this);
    } else {
      sSyllabusManager.create(this);
    }
  }

  @Override
  public void delete() throws Exception {
    sSyllabusManager.delete(this);
  }

  @Override
  public String getId() {
    return mId;
  }

  @Override
  public void setId(String pId) {
    mId = pId;
  }

  @Override
  public Semester getSemester() throws Exception {
    return mSemester == null ? sSemesterManager.get(mSemesterId) : mSemester;
  }

  @Override
  public void setSemester(Semester pSemester) {
    mSemester = pSemester;
  }

  @Override
  public Program getProgram() throws Exception {
    return mProgram == null ? sProgramManager.get(mProgramId) : mProgram;
  }

  @Override
  public void setProgram(Program pProgram) {
    mProgram = pProgram;
  }

  @Override
  public MutableSyllabus edit() throws Exception {
    return new PersistentSyllabus(this);
  }

  @Override
  public int getSemesterId() {
    return mSemesterId;
  }

  public void setSemesterId(int pSemesterId) {
    mSemesterId = pSemesterId;
  }

  @Override
  public int getProgramId() {
    return mProgramId;
  }

  public void setProgramId(int pProgramId) {
    mProgramId = pProgramId;
  }

  @Override
  public String getLastModified() {
    return mLastModified;
  }

  @Override
  public void setLastModified(final String pLastModified) {
    mLastModified = pLastModified;
  }
}
