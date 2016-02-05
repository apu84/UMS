package org.ums.domain.model.readOnly;


import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableProgram;

import java.io.Serializable;

public interface Program extends Serializable, EditType<MutableProgram>, LastModifier, Identifier<Integer> {

  ProgramType getProgramType() throws Exception;

  int getProgramTypeId();

  String getShortName() throws Exception;

  String getLongName() throws Exception;

  Department getDepartment() throws Exception;

  String getDepartmentId();
}
