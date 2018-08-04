package org.ums.manager;

import org.ums.domain.model.immutable.AbsLateComingInfo;
import org.ums.domain.model.mutable.MutableAbsLateComingInfo;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 7/1/2018.
 */
public interface AbsLateComingInfoManager extends ContentManager<AbsLateComingInfo, MutableAbsLateComingInfo, Long> {
  List<AbsLateComingInfo> getInfoBySemesterExamTypeAndExamDate(final Integer pSemesterId, final Integer pExamType,
      final String pExamDate);
}
