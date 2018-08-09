package org.ums.decorator;

import org.ums.domain.model.immutable.AbsLateComingInfo;
import org.ums.domain.model.mutable.MutableAbsLateComingInfo;
import org.ums.manager.AbsLateComingInfoManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 7/1/2018.
 */
public class AbsLateComingInfoDaoDecorator extends
    ContentDaoDecorator<AbsLateComingInfo, MutableAbsLateComingInfo, Long, AbsLateComingInfoManager> implements
    AbsLateComingInfoManager {
  @Override
  public List<AbsLateComingInfo> getInfoBySemesterExamTypeAndExamDate(Integer pSemesterId, Integer pExamType,
      String pExamDate) {
    return getManager().getInfoBySemesterExamTypeAndExamDate(pSemesterId, pExamType, pExamDate);
  }

  @Override
  public List<AbsLateComingInfo> getInfoBySemesterExamType(Integer pSemesterId, Integer pExamType) {
    return getManager().getInfoBySemesterExamType(pSemesterId, pExamType);
  }
}
