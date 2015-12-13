package org.ums.manager;


import org.ums.domain.model.MutableSemester;
import org.ums.domain.model.Semester;

import java.util.List;

public interface SemesterManager {
  List<Semester> getAll() throws Exception;

  Semester get(final String pSemesterId) throws Exception;

  void update(final MutableSemester pSemester) throws Exception;

  void delete(final MutableSemester pSemester) throws Exception;

  void create(final MutableSemester pSemester) throws Exception;
}
