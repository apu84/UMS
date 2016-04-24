package org.ums.decorator;

import org.ums.domain.model.immutable.SemesterWithdrawal;
import org.ums.domain.model.mutable.MutableSemesterWithdrawal;
import org.ums.manager.SemesterWithDrawalManager;

import java.util.List;

/**
 * Created by My Pc on 3/23/2016.
 */
public class SemesterWithdrawalDaoDecorator extends ContentDaoDecorator<SemesterWithdrawal,MutableSemesterWithdrawal,Integer,SemesterWithDrawalManager> implements SemesterWithDrawalManager {

  @Override
  public SemesterWithdrawal getStudentsRecord(String studentId,int semesterId,int year,int semester) {
    return getManager().getStudentsRecord(studentId,semesterId,year,semester);
  }

  @Override
  public List<SemesterWithdrawal> getByDeptForEmployee(String deptId) {
    return getManager().getByDeptForEmployee(deptId);
  }

  @Override
  public List<SemesterWithdrawal> getSemesterWithdrawalForHead(String teacherId) {
    return getManager().getSemesterWithdrawalForHead(teacherId);
  }

  @Override
  public List<SemesterWithdrawal> getSemesterWithdrawalForAAO(String employeeId) {
    return getManager().getSemesterWithdrawalForAAO(employeeId);
  }

  @Override
  public List<SemesterWithdrawal> getSemesterWithdrawalForVC(String employeeId) {
    return getManager().getSemesterWithdrawalForVC(employeeId);
  }

  @Override
  public List<SemesterWithdrawal> getSemesterWithdrawalForRegistrar(String employeeId) {
    return getManager().getSemesterWithdrawalForRegistrar(employeeId);
  }

  @Override
  public List<SemesterWithdrawal> getSemesterWithdrawalForDeputyRegistrar(String employeeId) {
    return getManager().getSemesterWithdrawalForDeputyRegistrar(employeeId);
  }
}
