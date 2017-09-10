package org.ums.employee.service;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.immutable.Designation;
import org.ums.domain.model.immutable.EmploymentType;

import java.io.Serializable;
import java.util.Date;

public interface ServiceInformation extends Serializable, EditType<MutableServiceInformation>, Identifier<Long>,
    LastModifier {

  String getEmployeeId();

  Department getDepartment();

  String getDepartmentId();

  Designation getDesignation();

  int getDesignationId();

  EmploymentType getEmployment();

  int getEmploymentId();

  Date getJoiningDate();

  Date getResignDate();
}
