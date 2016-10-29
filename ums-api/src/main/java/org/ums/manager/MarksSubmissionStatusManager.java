package org.ums.manager;

import java.util.List;

import org.ums.domain.model.immutable.MarksSubmissionStatus;
import org.ums.domain.model.mutable.MutableMarksSubmissionStatus;
import org.ums.enums.ExamType;

public interface MarksSubmissionStatusManager extends
    ContentManager<MarksSubmissionStatus, MutableMarksSubmissionStatus, Integer> {
  MarksSubmissionStatus get(Integer pSemesterId, String pCourseId, ExamType pExamType)
      throws Exception;

  List<MarksSubmissionStatus> get(Integer pProgramId, Integer pSemesterId) throws Exception;

  List<MarksSubmissionStatus> getByProgramType(Integer pProgramTypeId, Integer pSemesterId)
      throws Exception;

  boolean isValidForResultProcessing(Integer pProgramId, Integer pSemesterId) throws Exception;
}
