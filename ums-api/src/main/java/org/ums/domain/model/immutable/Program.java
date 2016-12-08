package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableProgram;

import java.io.Serializable;

public interface Program extends Serializable, EditType<MutableProgram>, LastModifier,
    Identifier<Integer> {

  ProgramType getProgramType();

  int getProgramTypeId();

  String getShortName();

  String getLongName();

  Department getDepartment();

  String getDepartmentId();

  Faculty getFaculty();

  int getFacultyId();
}
