package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Program;
import org.ums.domain.model.immutable.Routine;
import org.ums.domain.model.immutable.Semester;

/**
 * Created by My Pc on 3/5/2016.
 */
public interface MutableRoutine extends Routine, Editable<Long>, MutableLastModifier, MutableIdentifier<Long> {
  void setSemester(final Semester pSemester);

  void setProgram(final Program pProgram);

  void setCourseId(final String courseId);

  void setCourseNo(final String pCourseNo);

  void setDay(final int dayId);

  void setSection(final String pSection);

  void setAcademicYear(final int pAcademicYear);

  void setAcademicSemester(final int pAcademicSemester);

  void setStartTime(final String pStartTime);

  void setEndTime(final String pEndTime);

  void setRoomId(final Integer pRoomId);

  void setDuration(final int mDuration);

  void setStatus(final String pStatus);
}
