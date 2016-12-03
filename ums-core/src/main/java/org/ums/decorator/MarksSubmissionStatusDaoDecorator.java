package org.ums.decorator;

import java.util.List;

import org.ums.domain.model.immutable.MarksSubmissionStatus;
import org.ums.domain.model.mutable.MutableMarksSubmissionStatus;
import org.ums.enums.ExamType;
import org.ums.manager.MarksSubmissionStatusManager;

public class MarksSubmissionStatusDaoDecorator
    extends
    ContentDaoDecorator<MarksSubmissionStatus, MutableMarksSubmissionStatus, Integer, MarksSubmissionStatusManager>
    implements MarksSubmissionStatusManager {
  @Override
  public MarksSubmissionStatus get(Integer pSemesterId, String pCourseId, ExamType pExamType) {
    return getManager().get(pSemesterId, pCourseId, pExamType);
  }

  @Override
  public List<MarksSubmissionStatus> get(Integer pProgramId, Integer pSemesterId) {
    return getManager().get(pProgramId, pSemesterId);
  }

  @Override
  public List<MarksSubmissionStatus> getByProgramType(Integer pProgramTypeId, Integer pSemesterId) {
    return getManager().getByProgramType(pProgramTypeId, pSemesterId);
  }

  @Override
  public boolean isValidForResultProcessing(Integer pProgramId, Integer pSemesterId) {
    return getManager().isValidForResultProcessing(pProgramId, pSemesterId);
  }
}
