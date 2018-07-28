package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.immutable.Employee;
import org.ums.domain.model.mutable.MutableEmpExamAttendance;
import org.ums.manager.*;

/**
 * Created by Monjur-E-Morshed on 7/27/2018.
 */
public class PersistentEmpExamAttendance implements MutableEmpExamAttendance {
  private static DepartmentManager sDepartmentManager;
  private static EmployeeManager sEmployeeManager;
  private static EmpExamAttendanceManager sEmpExamAttendanceManager;
  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sDepartmentManager = applicationContext.getBean("departmentManager", DepartmentManager.class);
    sEmployeeManager = applicationContext.getBean("employeeManager", EmployeeManager.class);
    sEmpExamAttendanceManager = applicationContext.getBean("empExamAttendanceManager", EmpExamAttendanceManager.class);
  }
  private Long mId;
  private Integer mSemesterId;
  private Integer mExamType;
  private Department mDepartment;
  private Employee mEmployee;
  private String mEmployeeId;
  private String mEmployeeName;
  private String mDeptId;
  private Long mInvigilatorRoomId;
  private String mInvigilatorRoomName;
  private String mExamDate;
  private String mReserveDate;
  private Integer mRoomInCharge;

  public PersistentEmpExamAttendance() {

  }

  public PersistentEmpExamAttendance(PersistentEmpExamAttendance pPersistentEmpExamAttendance) {
    mId = pPersistentEmpExamAttendance.getId();
    mSemesterId = pPersistentEmpExamAttendance.getSemesterId();
    mExamType = pPersistentEmpExamAttendance.getExamType();
    mDeptId = pPersistentEmpExamAttendance.getDepartmentId();
    mDepartment = pPersistentEmpExamAttendance.getDepartment();
    mInvigilatorRoomId = pPersistentEmpExamAttendance.getInvigilatorRoomId();
    mInvigilatorRoomName = pPersistentEmpExamAttendance.getInvigilatorRoomName();
    mExamDate = pPersistentEmpExamAttendance.getExamDate();
    mReserveDate = pPersistentEmpExamAttendance.getReserveDate();
    mRoomInCharge = pPersistentEmpExamAttendance.getRoomInCharge();
    mEmployeeId = pPersistentEmpExamAttendance.getEmployeeId();
    mEmployeeName = pPersistentEmpExamAttendance.getEmployeeName();
    mEmployee = pPersistentEmpExamAttendance.getEmployees();
  }

  @Override
  public void setSemesterId(Integer pSemesterId) {
    mSemesterId = pSemesterId;
  }

  @Override
  public void setDepartmentId(String pDepartmentId) {
    mDeptId = pDepartmentId;
  }

  @Override
  public void setDepartment(Department pDepartment) {
    mDepartment = pDepartment;
  }

  @Override
  public void setExamType(Integer pExamType) {
    mExamType = pExamType;
  }

  @Override
  public void setInvigilatorRoomId(Long pInvigilatorRoomId) {
    mInvigilatorRoomId = pInvigilatorRoomId;
  }

  @Override
  public void setInvigilatorRoomName(String pInvigilatorRoomName) {
    mInvigilatorRoomName = pInvigilatorRoomName;
  }

  @Override
  public void setRoomInCharge(Integer pRoomInCharge) {
    mRoomInCharge = pRoomInCharge;
  }

  @Override
  public void setExamDate(String pExamDate) {
    mExamDate = pExamDate;
  }

  @Override
  public void setReserveDate(String pReserveDate) {
    mReserveDate = pReserveDate;
  }

  @Override
  public void setEmployeeId(String pEmployeeId) {
    mEmployeeId = pEmployeeId;
  }

  @Override
  public void setEmployeeName(String pEmployeeName) {
    mEmployeeName = pEmployeeName;
  }

  @Override
  public void setEmployees(Employee pEmployee) {
    mEmployee = pEmployee;
  }

  @Override
  public Long create() {
    return sEmpExamAttendanceManager.create(this);
  }

  @Override
  public void update() {
    sEmpExamAttendanceManager.update(this);
  }

  @Override
  public void delete() {
    sEmpExamAttendanceManager.delete(this);
  }

  @Override
  public void setId(Long pId) {
    mId = pId;
  }

  @Override
  public Long getId() {
    return mId;
  }

  @Override
  public Integer getSemesterId() {
    return mSemesterId;
  }

  @Override
  public String getDepartmentId() {
    return mDeptId;
  }

  @Override
  public Department getDepartment() {
    return mDepartment == null ? sDepartmentManager.get(mDeptId) : sDepartmentManager.validate(mDepartment);
  }

  @Override
  public Integer getExamType() {
    return mExamType;
  }

  @Override
  public Long getInvigilatorRoomId() {
    return mInvigilatorRoomId;
  }

  @Override
  public String getInvigilatorRoomName() {
    return mInvigilatorRoomName;
  }

  @Override
  public Integer getRoomInCharge() {
    return mRoomInCharge;
  }

  @Override
  public String getExamDate() {
    return mExamDate;
  }

  @Override
  public String getReserveDate() {
    return mReserveDate;
  }

  @Override
  public String getEmployeeId() {
    return mEmployeeId;
  }

  @Override
  public String getEmployeeName() {
    return mEmployeeName;
  }

  @Override
  public Employee getEmployees() {
    return mEmployee == null ? sEmployeeManager.get(mEmployeeId) : sEmployeeManager.validate(mEmployee);
  }

  @Override
  public MutableEmpExamAttendance edit() {
    return new PersistentEmpExamAttendance(this);
  }

  @Override
  public String getLastModified() {
    return null;
  }

  @Override
  public void setLastModified(String pLastModified) {

  }
}