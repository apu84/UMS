package org.ums.domain.model.readOnly;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.mutable.MutableEnrollmentFromTo;

import java.io.Serializable;

public interface EnrollmentFromTo extends Serializable, LastModifier, Identifier<Integer>, EditType<MutableEnrollmentFromTo> {
  Integer getProgramId();

  Program getProgram() throws Exception;

  Integer getFromYear();

  Integer getFromSemester();

  Integer getToYear();

  Integer getToSemester();
}
