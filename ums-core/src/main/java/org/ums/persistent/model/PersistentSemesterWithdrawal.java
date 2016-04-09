package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Program;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.SemesterWithdrawalLog;
import org.ums.domain.model.immutable.Student;
import org.ums.domain.model.mutable.MutableSemesterWithdrawal;
import org.ums.manager.ProgramManager;
import org.ums.manager.SemesterManager;
import org.ums.manager.SemesterWithDrawalManager;
import org.ums.manager.StudentManager;

import java.util.ArrayList;
import java.util.List;


public class PersistentSemesterWithdrawal implements MutableSemesterWithdrawal {

  private static SemesterManager sSemesterManager;
  private static ProgramManager sProgramManager;
  private static StudentManager sStudentManager;
  private static SemesterWithDrawalManager sSemesterWithDrawalManager;

  static{
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sSemesterManager = applicationContext.getBean("semesterManager",SemesterManager.class);
    sProgramManager = applicationContext.getBean("programManager",ProgramManager.class);
    sStudentManager = applicationContext.getBean("studentManager",StudentManager.class);
    sSemesterWithDrawalManager = applicationContext.getBean("semesterWithdrawalManager",SemesterWithDrawalManager.class);
  }

  private int mId;
  private Semester mSemester;
  private Program mProgram;
  private int mProgramId;
  private Student mStudent;
  private String mStudentId;
  private int mSemesterId;
  private String mCause;
  private String mLastModified;
  private List<SemesterWithdrawalLog> mLogs = new ArrayList<>();

  public PersistentSemesterWithdrawal(){

  }



  public PersistentSemesterWithdrawal(final PersistentSemesterWithdrawal pPersistentSemesterWithdrawal)throws Exception{
    mId = pPersistentSemesterWithdrawal.getId();

    mSemester = pPersistentSemesterWithdrawal.getSemester();
    mSemesterId = pPersistentSemesterWithdrawal.getSemesterId();
    mProgram = pPersistentSemesterWithdrawal.getProgram();
    mProgramId = pPersistentSemesterWithdrawal.getProgramId();
    mStudent = pPersistentSemesterWithdrawal.getStudent();
    mStudentId = pPersistentSemesterWithdrawal.getStudentId();
    mCause = pPersistentSemesterWithdrawal.getCause();
    mLastModified = pPersistentSemesterWithdrawal.getLastModified();
  }

  public String getStudentId() {
    return mStudentId;
  }

  public void setStudentId(String pStudentId) {
    mStudentId = pStudentId;
  }

  @Override
  public void setStudent(Student pStudent) {
    mStudent = pStudent;
  }

  @Override
  public Student getStudent() throws Exception {
    return mStudent==null?sStudentManager.get(mStudentId):sStudentManager.validate(mStudent);
  }

  public int getProgramId() {
    return mProgramId;
  }

  public void setProgramId(int pProgramId) {
    mProgramId = pProgramId;
  }

  @Override
  public void setProgram(Program pProgram) {
    mProgram = pProgram;
  }

  @Override
  public Program getProgram() throws Exception {
    return mProgram==null? sProgramManager.get(mProgramId): sProgramManager.validate(mProgram);
  }

  public int getSemesterId() {
    return mSemesterId;
  }

  public void setSemesterId(int pSemesterId) {
    mSemesterId = pSemesterId;
  }




  public void setId(int pId) {
    mId = pId;
  }



  @Override
  public void setSemester(Semester pSemester) {
    mSemester = pSemester;
  }

  @Override
  public void setCause(String pCause) {
    mCause = pCause;
  }






  @Override
  public Semester getSemester() throws Exception {
    return mSemester==null? sSemesterManager.get(mSemesterId): sSemesterManager.validate(mSemester);
  }

  @Override
  public String getCause() {
    return mCause;
  }





  @Override
  public MutableSemesterWithdrawal edit() throws Exception {
    return new PersistentSemesterWithdrawal(this);
  }

  @Override
  public Integer getId() {
    return mId;
  }

  @Override
  public String getLastModified() {
    return mLastModified;
  }

  @Override
  public void commit(boolean update) throws Exception {
    if(update){
      sSemesterWithDrawalManager.update(this);
    }else{
      sSemesterWithDrawalManager.create(this);
    }
  }

  @Override
  public void delete() throws Exception {
    sSemesterWithDrawalManager.delete(this);
  }

  @Override
  public void setId(Integer pId) {
    mId = pId;
  }

  @Override
  public void setLastModified(String pLastModified) {
    mLastModified = pLastModified;
  }
}
