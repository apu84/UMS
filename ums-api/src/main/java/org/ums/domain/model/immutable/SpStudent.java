package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableSpStudent;

import java.io.Serializable;

/**
 * Created by My Pc on 4/27/2016.
 */
public interface SpStudent extends Serializable,LastModifier,EditType<MutableSpStudent>,Identifier<String> {

  Program getProgram() throws Exception;
  Semester getSemester() throws Exception;
  int getAcademicYear();
  int getAcademicSemester();
  int getStatus();

}
