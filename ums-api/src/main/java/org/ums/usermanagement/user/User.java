package org.ums.usermanagement.user;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.immutable.Department;
import org.ums.usermanagement.role.Role;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public interface User extends Serializable, EditType<MutableUser>, Identifier<String>, LastModifier {
  char[] getPassword();

  String getEmployeeId();

  char[] getTemporaryPassword();

  boolean isActive();

  String getPasswordResetToken();

  Date getPasswordTokenGenerateDateTime();

  Integer getPrimaryRoleId();

  Role getPrimaryRole();

  Department getDepartment();

  String getDepartmentId();

  String getName();
}
