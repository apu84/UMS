package org.ums.manager;

import org.ums.domain.model.immutable.MarksSubmissionStatus;
import org.ums.domain.model.mutable.MutableMarksSubmissionStatus;
import org.ums.enums.ExamType;

public interface MarksSubmissionStatusManager
    extends ContentManager<MarksSubmissionStatus, MutableMarksSubmissionStatus, Integer> {
  MarksSubmissionStatus get(Integer pSemesterId, String pCourseId, ExamType pExamType) throws Exception;
}
