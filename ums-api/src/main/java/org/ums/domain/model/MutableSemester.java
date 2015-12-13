package org.ums.domain.model;


import java.util.Date;

public interface MutableSemester extends Semester {

  void setId(final String pId);

  void setName(final String pName);

  void setStartDate(final Date pDate);

  void setStatus(final boolean pStatus);

  void commit(final boolean update) throws Exception;

  void delete() throws Exception;
}
