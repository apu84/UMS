package org.ums.ems.createnew;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;

import java.io.Serializable;
import java.util.Date;

public interface EmployeeCreateRequest extends Serializable, LastModifier, EditType<MutableEmployeeCreateRequest>,
    Identifier<String> {

  String getEmail();

  Integer getSalutation();

  String getEmployeeName();

  String getAcademicInitial();

  String getDepartmentId();

  Integer getEmployeeType();

  Integer getDesignation();

  Integer getEmploymentType();

  Date getJoiningDate();

  Integer getCreateAccount();

  String getRequestedBy();

  Date getRequestedOn();

  String getActionTakenBy();

  Date getActionTakenOn();

  Integer getActionStatus();
}
