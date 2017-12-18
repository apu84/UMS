package org.ums.services.academic;

import java.util.List;

import org.ums.domain.model.immutable.StudentRecord;
import org.ums.domain.model.immutable.UGRegistrationResult;

public interface RemarksBuilder {
  String getGradeSheetRemarks(List<UGRegistrationResult> pResults, StudentRecord.Status pStatus, Integer pSemesterId);

  String getTabulationSheetRemarks(List<UGRegistrationResult> pResults, StudentRecord pStudentRecord,
      Integer pSemesterId);
}
