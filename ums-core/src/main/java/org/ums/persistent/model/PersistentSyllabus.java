package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.MutableSyllabus;
import org.ums.domain.model.immutable.Program;
import org.ums.domain.model.immutable.Semester;
import org.ums.manager.ProgramManager;
import org.ums.manager.SemesterManager;
import org.ums.manager.SyllabusManager;

public class PersistentSyllabus implements MutableSyllabus {
  private static SemesterManager sSemesterManager;
  private static ProgramManager sProgramManager;
  private static SyllabusManager sSyllabusManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sSemesterManager = applicationContext.getBean("semesterManager", SemesterManager.class);
    sProgramManager = applicationContext.getBean("programManager", ProgramManager.class);
    sSyllabusManager = applicationContext.getBean("syllabusManager", SyllabusManager.class);
  }

  private String mId;
  private Semester mSemester;
  private Program mProgram;
  private int mSemesterId;
  private int mProgramId;
  private String mLastModified;

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
    if(update) {
      sSyllabusManager.update(this);
    }
    else {
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
    return mSemester == null ? sSemesterManager.get(mSemesterId) : sSemesterManager
        .validate(mSemester);
  }

  @Override
  public void setSemester(Semester pSemester) {
    mSemester = pSemester;
  }

  @Override
  public Program getProgram() throws Exception {
    return mProgram == null ? sProgramManager.get(mProgramId) : sProgramManager.validate(mProgram);
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
