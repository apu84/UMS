package org.ums.domain.model.readOnly;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableSemester;

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
