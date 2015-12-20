package org.ums.domain.model;

public interface ProgramType extends EditType<MutableProgramType>, Cacheable<Integer> {
  String getName();
}
