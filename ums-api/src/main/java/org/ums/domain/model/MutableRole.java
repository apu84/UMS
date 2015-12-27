package org.ums.domain.model;

public interface MutableRole extends Role, Mutable, MutableIdentifier<Integer> {
  void setName(final String pName);
}
