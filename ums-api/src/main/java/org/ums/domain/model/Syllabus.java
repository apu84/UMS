package org.ums.domain.model;

import java.io.Serializable;

public interface Syllabus extends Serializable, EditType<MutableSyllabus>, LastModifier {

  String getId();

  Semester getSemester() throws Exception;

  Program getProgram() throws Exception;
}
