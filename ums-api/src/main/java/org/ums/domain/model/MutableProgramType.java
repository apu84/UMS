package org.ums.domain.model;

public interface MutableProgramType extends ProgramType, Mutable, MutableIdentifier<Integer> {
  void setName(final String pName);
}
