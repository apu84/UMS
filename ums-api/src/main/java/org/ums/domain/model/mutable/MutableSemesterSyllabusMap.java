package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Program;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.SemesterSyllabusMap;
import org.ums.domain.model.immutable.Syllabus;

/**
 * Created by Ifti on 08-Jan-16.
 */
public interface MutableSemesterSyllabusMap extends SemesterSyllabusMap, Editable<Integer>,
    MutableIdentifier<Integer>, MutableLastModifier {

  void setAcademicSemester(Semester semester);

  void setProgram(Program program);

  void setSyllabus(Syllabus syllabus);

  void setYear(final int year);

  void setSemester(final int semester);

}
