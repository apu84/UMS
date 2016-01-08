package org.ums.domain.model.regular;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.mutable.MutableUser;

import java.io.Serializable;

public interface User extends Serializable, EditType<MutableUser>, Identifier<String> {
  char[] getPassword();

  char[] getTemporaryPassword();

  Integer getRoleId();

  Role getRole() throws Exception;

  boolean isActive();
}
