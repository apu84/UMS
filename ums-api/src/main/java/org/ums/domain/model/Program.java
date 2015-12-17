package org.ums.domain.model;


import java.io.Serializable;

public interface Program extends Serializable, EditType<MutableProgram> {
  int getId() throws Exception;

  ProgramType getProgramType() throws Exception;

  String getShortName() throws Exception;

  String getLongName() throws Exception;

  Department getDepartment() throws Exception;
}
