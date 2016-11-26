package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableMarksSubmissionStatus;
import org.ums.enums.CourseMarksSubmissionStatus;
import org.ums.enums.ExamType;

import java.io.Serializable;
import java.util.Date;

public interface MarksSubmissionStatus extends Serializable,
    EditType<MutableMarksSubmissionStatus>, Identifier<Integer>, LastModifier {

  Integer getSemesterId();

  Semester getSemester() throws Exception;

  String getCourseId();

  Course getCourse() throws Exception;

  CourseMarksSubmissionStatus getStatus();

  ExamType getExamType();

  Integer getPartATotal();

  Integer getPartBTotal();

  Date getLastSubmissionDate();

  Integer getYear();

  Integer getAcademicSemester();

  Integer getTotalPart();
}
