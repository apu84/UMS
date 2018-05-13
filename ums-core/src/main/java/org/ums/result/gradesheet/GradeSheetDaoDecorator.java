package org.ums.result.gradesheet;

import org.ums.decorator.ContentDaoDecorator;

public class GradeSheetDaoDecorator extends
    ContentDaoDecorator<GradesheetModel, MutableGradesheetModel, Long, GradeSheetManager> implements GradeSheetManager {
  @Override
  public GradesheetModel get(String pStudentId, Integer pSemesterId) {
    return getManager().get(pStudentId, pSemesterId);
  }
}
