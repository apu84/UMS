package org.ums.domain.model.readOnly;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.mutable.MutableUser;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public interface User extends Serializable, EditType<MutableUser>, Identifier<String> {
  char[] getPassword();

  char[] getTemporaryPassword();

  List<Integer> getRoleIds();

  List<Role> getRoles() throws Exception;

  boolean isActive();

  String getPasswordResetToken();

  Date getPasswordTokenGenerateDateTime();

  boolean isPasswordResetTokenValid();

  Integer getPrimaryRoleId();

  Role getPrimaryRole() throws Exception;

  List<String> getAdditionalPermissions();
}
