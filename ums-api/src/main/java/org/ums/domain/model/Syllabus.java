package org.ums.domain.model;

import java.io.Serializable;

public interface Syllabus extends Serializable {

  String getId();

  String getName();

  Semester getSemester();

  MutableSyllabus edit() throws Exception;
}
