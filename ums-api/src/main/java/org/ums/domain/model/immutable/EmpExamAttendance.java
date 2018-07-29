package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableEmpExamAttendance;

import java.io.Serializable;

/**
 * Created by Monjur-E-Morshed on 7/27/2018.
 */
public interface EmpExamAttendance extends Serializable, LastModifier, EditType<MutableEmpExamAttendance>,
    Identifier<Long> {
  Long getId();

  Integer getSemesterId();

  String getDepartmentId();

  Department getDepartment();

  Integer getExamType();

  Long getInvigilatorRoomId();

  String getInvigilatorRoomName();

  Integer getRoomInCharge();

  String getExamDate();

  String getInvigilatorDate();

  String getReserveDate();

  String getEmployeeId();

  String getEmployeeName();

  Employee getEmployees();
}
