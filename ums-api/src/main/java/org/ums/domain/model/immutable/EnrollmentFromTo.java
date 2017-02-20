package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableEnrollmentFromTo;

import java.io.Serializable;

public interface EnrollmentFromTo extends Serializable, LastModifier, Identifier<Long>,
    EditType<MutableEnrollmentFromTo> {
  Integer getProgramId();

  Program getProgram();

  Integer getFromYear();

  Integer getFromSemester();

  Integer getToYear();

  Integer getToSemester();
}
