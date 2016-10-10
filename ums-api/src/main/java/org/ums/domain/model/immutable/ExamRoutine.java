package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.dto.ExamRoutineDto;
import org.ums.domain.model.mutable.MutableExamRoutine;
import java.io.Serializable;
import java.util.List;

public interface ExamRoutine extends Serializable, EditType<MutableExamRoutine> {
  List<ExamRoutineDto> getRoutine();
  Integer getSemesterId();
  String getSemesterName();
  Integer getExamTypeId();
  String getExamTypeName();
  String getInsertType();
  Integer getTotalStudent();
  String getExamDate();
  String getExamDateOriginal();
  String getProgramName();
  String getCourseNumber();
  String getCourseTitle();
  Integer getCourseYear();
  Integer getCourseSemester();
  Integer getExamGroup();
}
