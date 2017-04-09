package org.ums.domain.model.immutable.registrar.Employee;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.registrar.Employee.MutableEmployeeInformation;

import java.io.Serializable;
import java.util.Date;

public interface EmployeeInformation extends Serializable, EditType<MutableEmployeeInformation>, Identifier<Integer>,
    LastModifier {

  int getEmployeeId();

  String getEmploymentType();

  int getDesignation();

  String getDeptOffice();

  String getJoiningDate();

  String getJobPermanentDate();

  String getJobTerminationDate();

  int getExtNo();

  String getShortName();

  String getRoomNo();
}
