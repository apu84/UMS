package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.SemesterWithdrawal;
import org.ums.domain.model.immutable.SemesterWithdrawalLog;

import java.sql.Timestamp;


public interface MutableSemesterWithdrawalLog extends SemesterWithdrawalLog,Mutable,MutableLastModifier,MutableIdentifier<Integer> {
  void setSemesterWithdrawal(final SemesterWithdrawal pSemesterWithdrawal);
  void setActorId(final String pId);
  void setActor(final int pActor);
  void setAction(final int pAction);
  void setEventDate(final String mDate);
  void setComments(final String pComments);
}
