package org.ums.manager;

import org.ums.domain.model.immutable.SemesterWithdrawalLog;
import org.ums.domain.model.mutable.MutableSemesterWithdrawalLog;

/**
 * Created by My Pc on 3/25/2016.
 */
public interface SemesterWithdrawalLogManager extends ContentManager<SemesterWithdrawalLog,MutableSemesterWithdrawalLog,Integer> {
  public SemesterWithdrawalLog getForStudent(final String pStudentId,final int pSemesterId);
}
