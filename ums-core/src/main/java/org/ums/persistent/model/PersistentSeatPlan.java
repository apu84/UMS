package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.ClassRoom;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.SpStudent;
import org.ums.domain.model.mutable.MutableSeatPlan;
import org.ums.manager.ClassRoomManager;
import org.ums.manager.SeatPlanManager;
import org.ums.manager.SemesterManager;
import org.ums.manager.SpStudentManager;

/**
 * Created by My Pc on 5/8/2016.
 */
public class PersistentSeatPlan implements MutableSeatPlan {

  private static ClassRoomManager sClassRoomManager;
  private static SpStudentManager sSpStudentManager;
  private static SemesterManager sSemesterManager;
  private static SeatPlanManager sSeatPlanManager;

  static{
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sClassRoomManager = applicationContext.getBean("classRoomManager",ClassRoomManager.class);
    sSpStudentManager = applicationContext.getBean("spStudentManager",SpStudentManager.class);
    sSemesterManager = applicationContext.getBean("semesterManager",SemesterManager.class);
    sSeatPlanManager = applicationContext.getBean("seatPlanManager",SeatPlanManager.class);
  }

  private int mId;
  private ClassRoom mClassRoom;
  private int mClassRoomId;
  private SpStudent mSpStudent;
  private String mStudentId;
  private Semester mSemester;
  private int mSemesterId;
  private int mRowNo;
  private int mColNo;
  private int mGroupNo;
  private int mExamType;
  private String mLastModified;


  public PersistentSeatPlan(){

  }

  public PersistentSeatPlan(final PersistentSeatPlan pPersistentSeatPlan) throws Exception{
    mId = pPersistentSeatPlan.getId();
    mClassRoom = pPersistentSeatPlan.getClassRoom();
    mClassRoomId = pPersistentSeatPlan.getClassRoomId();
    mSpStudent = pPersistentSeatPlan.getStudent();
    mStudentId = pPersistentSeatPlan.getStudentId();
    mSemester = pPersistentSeatPlan.getSemester();
    mSemesterId = pPersistentSeatPlan.getSemesterId();
    mRowNo = pPersistentSeatPlan.getRowNo();
    mColNo = pPersistentSeatPlan.getColumnNo();
    mGroupNo = pPersistentSeatPlan.getGroupNo();
    mExamType = pPersistentSeatPlan.getExamType();
    mLastModified = pPersistentSeatPlan.getLastModified();
  }


  public int getClassRoomId() {
    return mClassRoomId;
  }

  public void setClassRoomId(int pClassRoomId) {
    mClassRoomId = pClassRoomId;
  }

  public String getStudentId() {
    return mStudentId;
  }

  public void setStudentId(String pStudentId) {
    mStudentId = pStudentId;
  }

  public int getSemesterId() {
    return mSemesterId;
  }

  public void setSemesterId(int pSemesterId) {
    mSemesterId = pSemesterId;
  }

  @Override
  public void setClassRoom(ClassRoom pClassRoom) {
    mClassRoom = pClassRoom;
  }

  @Override
  public void setStudent(SpStudent pStudent) {
    mSpStudent = pStudent;
  }

  @Override
  public void setSemester(Semester pSemester) {
    mSemester = pSemester;
  }

  @Override
  public void setRowNo(int pRowNo) {
    mRowNo = pRowNo;
  }

  @Override
  public void setColumnNo(int pColumnNo) {
    mColNo = pColumnNo;
  }

  @Override
  public void setExamType(int pExamType) {
    mExamType = pExamType;
  }

  @Override
  public void setGroupNo(int pGroupNo) {
    mGroupNo = pGroupNo;
  }

  @Override
  public void commit(boolean update) throws Exception {
    if(update){
      sSeatPlanManager.update(this);
    }else{
      sSeatPlanManager.create(this);
    }
  }

  @Override
  public void delete() throws Exception {
    sSeatPlanManager.delete(this);
  }

  @Override
  public void setId(Integer pId) {
    mId = pId;
  }

  @Override
  public void setLastModified(String pLastModified) {
    mLastModified = pLastModified;
  }

  @Override
  public ClassRoom getClassRoom() throws Exception {
    return mClassRoom==null?sClassRoomManager.get(mClassRoomId):sClassRoomManager.validate(mClassRoom);
  }

  @Override
  public SpStudent getStudent() throws Exception {
    return mSpStudent==null? sSpStudentManager.get(mStudentId):sSpStudentManager.validate(mSpStudent);
  }

  @Override
  public Semester getSemester() throws Exception {
    return mSemester==null? sSemesterManager.get(mSemesterId):sSemesterManager.validate(mSemester);
  }

  @Override
  public int getRowNo() {
    return mRowNo;
  }

  @Override
  public int getColumnNo() {
    return mColNo;
  }

  @Override
  public int getExamType() {
    return mExamType;
  }

  @Override
  public int getGroupNo() {
    return mGroupNo;
  }

  @Override
  public MutableSeatPlan edit() throws Exception {
    return new PersistentSeatPlan(this);
  }

  @Override
  public Integer getId() {
    return mId;
  }

  @Override
  public String getLastModified() {
    return mLastModified;
  }
}