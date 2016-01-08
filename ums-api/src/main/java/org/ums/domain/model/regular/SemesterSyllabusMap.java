package org.ums.domain.model.regular;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.mutable.MutableCourse;
import org.ums.domain.model.mutable.MutableSemesterSyllabusMap;

import java.io.Serializable;

/**
 * Created by Ifti on 08-Jan-16.
 */
public interface SemesterSyllabusMap extends Serializable, EditType<MutableSemesterSyllabusMap>, Identifier<Integer> {
   Semester getAcademicSemester();
   Program getProgram();
   Syllabus getSyllabus();
   int getYear();
   int getSemester();
}

