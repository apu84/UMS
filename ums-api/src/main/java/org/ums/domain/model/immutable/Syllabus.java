package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableSyllabus;

import java.io.Serializable;

public interface Syllabus extends Serializable, EditType<MutableSyllabus>, LastModifier, Identifier<String> {

  Semester getSemester() throws Exception;

  Program getProgram() throws Exception;

  int getSemesterId();

  int getProgramId();
}
