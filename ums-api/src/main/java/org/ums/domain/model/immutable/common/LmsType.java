package org.ums.domain.model.immutable.common;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.common.MutableLmsType;
import org.ums.enums.common.DurationType;
import org.ums.enums.common.EmployeeLeaveType;
import org.ums.enums.common.SalaryType;
import org.ums.usermanagement.role.Role;

import java.io.Serializable;

/**
 * Created by Monjur-E-Morshed on 03-May-17.
 */
public interface LmsType extends Serializable, EditType<MutableLmsType>, LastModifier, Identifier<Integer> {

  String getName();

  EmployeeLeaveType getEmployeeLeaveType();

  int getDuration();

  int getMaxDuration();

  int getMaxSimultaneousDuration();

  DurationType getDurationType();

  SalaryType getSalaryType();

  int getAuthorizeRoleId();

  Role getAuthorizeRole();

  int getSpecialAuthorizeRoleId();

  Role getSpecialAuthorizeRole();

}
