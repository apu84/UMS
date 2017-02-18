package org.ums.manager;

import org.ums.domain.model.immutable.SemesterWithdrawal;
import org.ums.domain.model.mutable.MutableSemesterWithdrawal;

import javax.ws.rs.core.Context;
import java.util.List;

public interface SemesterWithDrawalManager extends
    ContentManager<SemesterWithdrawal, MutableSemesterWithdrawal, Long> {
  public SemesterWithdrawal getStudentsRecord(String studentId, int semesterId, int year,
      int semester);

  public List<SemesterWithdrawal> getByDeptForEmployee(String deptId);

  public List<SemesterWithdrawal> getSemesterWithdrawalForHead(String teacherId);

  public List<SemesterWithdrawal> getSemesterWithdrawalForAAO(String employeeId);

  public List<SemesterWithdrawal> getSemesterWithdrawalForVC(String employeeId);

  public List<SemesterWithdrawal> getSemesterWithdrawalForRegistrar(String employeeId);

  public List<SemesterWithdrawal> getSemesterWithdrawalForDeputyRegistrar(String employeeId);
}
