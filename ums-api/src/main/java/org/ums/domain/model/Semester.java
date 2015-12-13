package org.ums.domain.model;

import java.io.Serializable;
import java.util.Date;

public interface Semester extends Serializable {

  String getId();

  String getName();

  Date getStartDate();

  boolean getStatus();

  MutableSemester edit() throws Exception;
}
