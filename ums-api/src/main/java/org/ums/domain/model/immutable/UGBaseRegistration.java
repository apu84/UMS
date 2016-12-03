package org.ums.domain.model.immutable;

import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.enums.CourseRegType;
import org.ums.enums.ExamType;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public interface UGBaseRegistration extends Serializable, Identifier<Integer>, LastModifier {
  String getCourseId();

  Course getCourse(); // will provide course_id,course_no and course_title for
                      // UG_Registration_Result.

  Integer getSemesterId();

  Semester getSemester();

  String getStudentId();

  Student getStudent();

  String getGradeLetter();

  ExamType getExamType();

  CourseRegType getType(); // Type="Carry","Clearance","Improvement"

  String getCourseNo();

  String getCourseTitle();

  String getExamDate(); // is required for getting exam date for clearance exam.

  String getMessage();
}
