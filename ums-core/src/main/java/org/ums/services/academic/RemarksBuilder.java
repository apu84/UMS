package org.ums.services.academic;

import org.ums.domain.model.immutable.StudentRecord;
import org.ums.domain.model.immutable.UGRegistrationResult;

import java.util.List;

public interface RemarksBuilder {
  String getRemarks(List<UGRegistrationResult> pResults, StudentRecord.Status pStatus, Integer pSemesterId);
}
