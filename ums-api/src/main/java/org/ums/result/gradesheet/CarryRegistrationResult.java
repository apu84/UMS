package org.ums.result.gradesheet;

import org.ums.domain.model.immutable.UGRegistrationResult;

public interface CarryRegistrationResult extends UGRegistrationResult {
  String getRegularYear();

  String getRegularSemester();
}
