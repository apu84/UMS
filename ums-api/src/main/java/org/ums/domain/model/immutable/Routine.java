package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableRoutine;

import java.io.Serializable;
import java.time.LocalTime;

/**
 * Created by My Pc on 3/5/2016.
 */
public interface Routine extends Serializable, LastModifier, EditType<MutableRoutine>, Identifier<Long> {

  Integer getSemesterId();

  Semester getSemester();

  Integer getProgramId();

  Program getProgram();

  String getCourseId();

  Course getCourse();

  int getDay();

  String getSection();

  int getAcademicYear();

  int getAcademicSemester();

  LocalTime getStartTime();

  LocalTime getEndTime();

  int getDuration();

  Long getRoomId();

  ClassRoom getRoom();

  String getStatus();
}
