package org.ums.manager;

import org.ums.domain.model.immutable.SemesterWithdrawal;
import org.ums.domain.model.mutable.MutableSemesterWithdrawal;

import javax.ws.rs.core.Context;
import java.util.List;

public interface SemesterWithDrawalManager extends ContentManager<SemesterWithdrawal, MutableSemesterWithdrawal, Long> {
  SemesterWithdrawal getStudentsRecord(String studentId, int semesterId, int year, int semester);

  List<SemesterWithdrawal> getByDeptForEmployee(String deptId);

  List<SemesterWithdrawal> getSemesterWithdrawalForHead(String teacherId);

  List<SemesterWithdrawal> getSemesterWithdrawalForAAO(String employeeId);

  List<SemesterWithdrawal> getSemesterWithdrawalForVC(String employeeId);

  List<SemesterWithdrawal> getSemesterWithdrawalForRegistrar(String employeeId);

  List<SemesterWithdrawal> getSemesterWithdrawalForDeputyRegistrar(String employeeId);
}
