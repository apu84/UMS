package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.immutable.EmpExamAttendance;
import org.ums.domain.model.immutable.Employee;

/**
 * Created by Monjur-E-Morshed on 7/27/2018.
 */
public interface MutableEmpExamAttendance extends EmpExamAttendance, Editable<Long>, MutableLastModifier,
    MutableIdentifier<Long> {
  void setId(final Long pId);

  void setSemesterId(final Integer pSemesterId);

  void setDepartmentId(final String pDepartmentId);

  void setDepartment(final Department pDepartment);

  void setExamType(final Integer pExamType);

  void setInvigilatorRoomId(final Long pInvigilatorRoomId);

  void setInvigilatorRoomName(final String pInvigilatorRoomName);

  void setRoomInCharge(final Integer pRoomInCharge);

  void setExamDate(final String pExamDate);

  void setReserveDate(final String pReserveDate);

  void setEmployeeId(final String pEmployeeId);

  void setEmployeeName(final String pEmployeeName);

  void setEmployees(final Employee pEmployee);
}
