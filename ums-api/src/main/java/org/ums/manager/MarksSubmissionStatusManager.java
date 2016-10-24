package org.ums.manager;

import org.ums.domain.model.immutable.MarksSubmissionStatus;
import org.ums.domain.model.mutable.MutableMarksSubmissionStatus;
import org.ums.enums.ExamType;

import java.util.List;

public interface MarksSubmissionStatusManager extends
    ContentManager<MarksSubmissionStatus, MutableMarksSubmissionStatus, Integer> {
  MarksSubmissionStatus get(Integer pSemesterId, String pCourseId, ExamType pExamType)
      throws Exception;
  List<MarksSubmissionStatus> get(Integer pProgramId, Integer pSemesterId) throws Exception;

  boolean isValidForResultProcessing(Integer pProgramId, Integer pSemesterId) throws Exception;
}
