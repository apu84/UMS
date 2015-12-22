package org.ums.domain.model;

public interface ProgramType extends EditType<MutableProgramType>, Identifier<Integer> {
  String getName();
}
