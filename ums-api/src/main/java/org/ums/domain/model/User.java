package org.ums.domain.model;

import java.io.Serializable;

public interface User extends Serializable, EditType<MutableUser>, Identifier<String> {
  char[] getPassword();

  Integer getRoleId();

  Role getRole() throws Exception;

  boolean isActive();
}
