package org.ums.decorator;

import org.ums.domain.model.immutable.MarksSubmissionStatus;
import org.ums.domain.model.mutable.MutableMarksSubmissionStatus;
import org.ums.enums.ExamType;
import org.ums.manager.MarksSubmissionStatusManager;

public class MarksSubmissionStatusDaoDecorator
    extends
    ContentDaoDecorator<MarksSubmissionStatus, MutableMarksSubmissionStatus, Integer, MarksSubmissionStatusManager>
    implements MarksSubmissionStatusManager {
  @Override
  public MarksSubmissionStatus get(Integer pSemesterId, String pCourseId, ExamType pExamType)
      throws Exception {
    return getManager().get(pSemesterId, pCourseId, pExamType);
  }
}
