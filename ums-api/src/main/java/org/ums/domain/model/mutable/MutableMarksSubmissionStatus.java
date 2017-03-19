package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.MarksSubmissionStatus;
import org.ums.domain.model.immutable.Semester;
import org.ums.enums.CourseMarksSubmissionStatus;
import org.ums.enums.ExamType;

import java.util.Date;

public interface MutableMarksSubmissionStatus extends MarksSubmissionStatus, Editable<Long>, MutableIdentifier<Long>,
    MutableLastModifier {

  void setSemesterId(Integer pSemesterId);

  void setSemester(Semester pSemester);

  void setCourseId(String pCourseId);

  void setCourse(Course pCourse);

  void setStatus(CourseMarksSubmissionStatus pStatus);

  void setExamType(ExamType pExamType);

  void setLastSubmissionDatePrep(Date pLastSubmissionDatePrep);

  void setLastSubmissionDateScr(Date pLastSubmissionDateScr);

  void setLastSubmissionDateHead(Date pLastSubmissionDateHead);

  void setPartATotal(Integer pPartATotal);

  void setPartBTotal(Integer pPartBTotal);

  void setYear(Integer pYear);

  void setAcademicSemester(Integer pSemester);

  void setTotalPart(Integer pTotalPart);
}
