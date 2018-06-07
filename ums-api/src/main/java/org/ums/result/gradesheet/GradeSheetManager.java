package org.ums.result.gradesheet;

import org.ums.manager.ContentManager;

public interface GradeSheetManager extends ContentManager<GradesheetModel, MutableGradesheetModel, Long> {
  GradesheetModel get(String pStudentId, Integer pSemesterId);
}
