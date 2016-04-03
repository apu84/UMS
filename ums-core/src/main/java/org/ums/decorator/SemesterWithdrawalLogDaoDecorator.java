package org.ums.decorator;

import org.ums.cache.ContentDaoDecorator;
import org.ums.domain.model.immutable.SemesterWithdrawalLog;
import org.ums.domain.model.mutable.MutableSemesterWithdrawalLog;
import org.ums.manager.SemesterWithdrawalLogManager;

/**
 * Created by My Pc on 3/25/2016.
 */
public class SemesterWithdrawalLogDaoDecorator extends ContentDaoDecorator<SemesterWithdrawalLog,MutableSemesterWithdrawalLog,Integer,SemesterWithdrawalLogManager> implements SemesterWithdrawalLogManager {

  @Override
  public SemesterWithdrawalLog getForStudent(String pStudentId,int semesterId) {
    return getManager().getForStudent(pStudentId,semesterId);
  }
}
