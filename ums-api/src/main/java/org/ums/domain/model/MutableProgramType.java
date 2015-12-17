package org.ums.domain.model;

public interface MutableProgramType extends ProgramType, Mutable {
  void setId(final int pId);

  void setName(final String pName);
}
