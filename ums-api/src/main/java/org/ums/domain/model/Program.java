package org.ums.domain.model;


import java.io.Serializable;

public interface Program extends Serializable, EditType<MutableProgram>, LastModifier, Identifier<Integer> {

  ProgramType getProgramType() throws Exception;

  int getProgramTypeId();

  String getShortName() throws Exception;

  String getLongName() throws Exception;

  Department getDepartment() throws Exception;

  int getDepartmentId();
}
