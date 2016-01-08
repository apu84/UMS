package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.regular.Program;
import org.ums.domain.model.regular.Semester;
import org.ums.domain.model.regular.SemesterSyllabusMap;
import org.ums.domain.model.regular.Syllabus;

/**
 * Created by Ifti on 08-Jan-16.
 */
public interface MutableSemesterSyllabusMap extends SemesterSyllabusMap, Mutable, MutableIdentifier<Integer> {

  void  setAcademicSemester(Semester semester);
  void setProgram(Program program);
  void setSyllabus(Syllabus syllabus);
  void  setYear(final int year);
  void  setSemester(final int semester);

}
