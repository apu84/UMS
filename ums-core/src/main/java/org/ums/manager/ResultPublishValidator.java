package org.ums.manager;

import org.ums.decorator.ResultPublishDaoDecorator;
import org.ums.domain.model.immutable.MarksSubmissionStatus;
import org.ums.enums.CourseMarksSubmissionStatus;

import java.util.List;

public class ResultPublishValidator extends ResultPublishDaoDecorator {
  private MarksSubmissionStatusManager mMarksSubmissionStatusManager;

  public ResultPublishValidator(MarksSubmissionStatusManager pMarksSubmissionStatusManager) {
    mMarksSubmissionStatusManager = pMarksSubmissionStatusManager;
  }

  @Override
  public void publishResult(Integer programId, Integer semesterId) throws Exception {
    if(!super.isResultPublished(programId, semesterId)) {
      List<MarksSubmissionStatus> marksSubmissionStatuses =
          mMarksSubmissionStatusManager.get(programId, semesterId);
      for(MarksSubmissionStatus status : marksSubmissionStatuses) {
        if(status.getStatus() != CourseMarksSubmissionStatus.ACCEPTED_BY_COE) {
          throw new IllegalArgumentException("Marks for course " + status.getCourse().getNo()
              + " is not accepted by CoE");
        }
      }
      super.publishResult(programId, semesterId);
    }
    else {
      throw new IllegalArgumentException("Result already processed");
    }
  }
}
