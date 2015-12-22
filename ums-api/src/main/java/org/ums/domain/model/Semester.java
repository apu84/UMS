package org.ums.domain.model;

import java.io.Serializable;
import java.util.Date;

public interface Semester extends Serializable, EditType<MutableSemester>, LastModifier, Identifier<Integer> {
  String getName() throws Exception;

  Date getStartDate() throws Exception;

  Date getEndDate() throws Exception;

  ProgramType getProgramType() throws Exception;

  int getProgramTypeId();

  boolean getStatus() throws Exception;
}
