package org.ums.services.academic;

import org.ums.domain.model.immutable.TaskStatus;
import org.ums.response.type.GenericResponse;

public interface ProcessResult {
  String PROCESS_GRADES = "_process_grades";
  String PROCESS_GRADES_TASK_NAME = "Processing student grades";
  String PROCESS_GPA_CGPA_PROMOTION = "_process_gpa_cgpa_promotion";
  String PROCESS_GPA_CGPA_PROMOTION_TASK_NAME = "Processing student GPA, CGPA and PASS/FAIL status";
  String PUBLISH_RESULT = "_publish_result";
  String PUBLISH_RESULT_TASK_NAME = "Publish result";

  void process(final int pProgramId, final int pSemesterId) throws Exception;

  GenericResponse<TaskStatus> status(int pProgramId, final int pSemesterId) throws Exception;

  void publishResult(final int pProgramId, final int pSemesterId) throws Exception;
}
