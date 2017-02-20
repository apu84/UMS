package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableSemesterWithdrawalLog;

import java.io.Serializable;
import java.sql.Timestamp;

public interface SemesterWithdrawalLog extends Serializable, LastModifier,
    EditType<MutableSemesterWithdrawalLog>, Identifier<Long> {
  public SemesterWithdrawal getSemesterWithdrawal();

  public String getEmployeeId();

  public String getEventDateTime();

  public int getAction();
}
