package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableEmployee;
import org.ums.ems.profilemanagement.personal.PersonalInformation;

import java.io.Serializable;
import java.util.Date;

public interface Employee extends Serializable, LastModifier, EditType<MutableEmployee>, Identifier<String> {

  int getDesignationId();

  Designation getDesignation();

  String getEmploymentType();

  Department getDepartment();

  Date getJoiningDate();

  int getStatus();

  String getShortName();

  int getEmployeeType();

  PersonalInformation getPersonalInformation();
}
