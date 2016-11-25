package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.MarksSubmissionStatus;
import org.ums.domain.model.immutable.Semester;
import org.ums.enums.CourseMarksSubmissionStatus;
import org.ums.enums.ExamType;

import java.util.Date;

public interface MutableMarksSubmissionStatus extends MarksSubmissionStatus, Mutable,
    MutableIdentifier<Integer>, MutableLastModifier {

  void setSemesterId(Integer pSemesterId);

  void setSemester(Semester pSemester);

  void setCourseId(String pCourseId);

  void setCourse(Course pCourse);

  void setStatus(CourseMarksSubmissionStatus pStatus);

  void setExamType(ExamType pExamType);

  void setLastSubmissionDate(Date pLastSubmissionDate);

  void setPartATotal(Integer pPartATotal);

  void setPartBTotal(Integer pPartBTotal);

  void setYear(Integer pYear);

  void setAcademicSemester(Integer pSemester);

  void setTotalPart(Integer pTotalPart);
}
