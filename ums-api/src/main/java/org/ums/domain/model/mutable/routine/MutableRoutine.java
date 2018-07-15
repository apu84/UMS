package org.ums.domain.model.mutable.routine;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.ClassRoom;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.Program;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.routine.Routine;
import org.ums.domain.model.mutable.MutableLastModifier;

import java.time.LocalTime;

/**
 * Created by My Pc on 3/5/2016.
 */
public interface MutableRoutine extends Routine, Editable<Long>, MutableLastModifier, MutableIdentifier<Long> {
  void setSemester(final Semester pSemester);

  void setSemesterId(final Integer pSemesterId);

  void setProgram(final Program pProgram);

  void setProgramId(final Integer pProgramId);

  void setCourseId(final String courseId);

  void setCourse(final Course pCourse);

  void setDay(final int dayId);

  void setSection(final String pSection);

  void setAcademicYear(final int pAcademicYear);

  void setAcademicSemester(final int pAcademicSemester);

  void setStartTime(final LocalTime pStartTime);

  void setEndTime(final LocalTime pEndTime);

  void setRoomId(final Long pRoomId);

  void setRoom(final ClassRoom pClassRoom);

  void setDuration(final int mDuration);

  void setStatus(final String pStatus);

  void setSlotGroup(final int pSlotGroup);
}
