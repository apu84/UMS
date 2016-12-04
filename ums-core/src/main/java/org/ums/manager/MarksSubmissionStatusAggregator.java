package org.ums.manager;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.ums.decorator.MarksSubmissionStatusDaoDecorator;
import org.ums.domain.model.immutable.MarksSubmissionStatus;
import org.ums.enums.CourseMarksSubmissionStatus;
import org.ums.enums.ExamType;

public class MarksSubmissionStatusAggregator extends MarksSubmissionStatusDaoDecorator {
  @Override
  public List<MarksSubmissionStatus> get(Integer pProgramId, Integer pSemesterId) {
    List<MarksSubmissionStatus> marksSubmissionStatuses = super.get(pProgramId, pSemesterId);
    List<MarksSubmissionStatus> filteredList = marksSubmissionStatuses.stream()
        .filter(pResult -> pResult.getCourse().getSyllabus().getProgramId() == pProgramId)
        .collect(Collectors.toList());

    return removeDuplicates(filteredList);
  }

  @Override
  public List<MarksSubmissionStatus> getByProgramType(Integer pProgramTypeId, Integer pSemesterId) {
    // Don't need to pass programTypeId, as it is not processed by persistent layer anyway
    List<MarksSubmissionStatus> marksSubmissionStatuses = super.get(-1, pSemesterId);
    List<MarksSubmissionStatus> filteredList =
        marksSubmissionStatuses.stream().filter(pResult -> pResult.getCourse().getSyllabus()
            .getProgram().getProgramTypeId() == pProgramTypeId).collect(Collectors.toList());

    return removeDuplicates(filteredList);
  }

  private List<MarksSubmissionStatus> removeDuplicates(List<MarksSubmissionStatus> pList) {
    Iterator<MarksSubmissionStatus> iterator = pList.iterator();
    while(iterator.hasNext()) {
      if(iterator.next().getExamType() == ExamType.CLEARANCE_CARRY_IMPROVEMENT) {
        iterator.next();
        iterator.remove();
      }
    }
    return pList;
  }

  @Override
  public boolean isValidForResultProcessing(Integer pProgramId, Integer pSemesterId) {
    List<MarksSubmissionStatus> marksSubmissionStatuses = get(pProgramId, pSemesterId);
    return marksSubmissionStatuses.stream()
        .filter(p -> p.getStatus() != CourseMarksSubmissionStatus.ACCEPTED_BY_COE)
        .findFirst() != null;
  }
}
