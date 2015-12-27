package org.ums.domain.model;

public interface MutableUser extends User, Mutable, MutableIdentifier<String> {
  void setPassword(final char[] pPassword);

  void setRoleId(final Integer pRoleId);
  
  void setRole(final Role pRole);

  void setActive(final boolean pActive);
}
