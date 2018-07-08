package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.AbsLateComingInfo;

/**
 * Created by Monjur-E-Morshed on 7/1/2018.
 */
public interface MutableAbsLateComingInfo extends AbsLateComingInfo, Editable<Long>, MutableLastModifier,
    MutableIdentifier<Long> {
  void setId(final Long pId);

  void setSemesterId(final Integer pSemesterId);

  void setExamType(final Integer pSetExamType);

  void setPresentType(final Integer pPresentType);

  void setPresentInfo(final String pPresentInfo);

  void setRemarks(final String pRemarks);

  void setEmployeeId(final String pEmployeeId);

  void setEmployeeName(final String pEmployeeName);

  void setEmployeeType(final String pEmployeeType);

  void setDeptId(final String pDeptId);

  void setDeptName(final String pDeptName);

  void setInvigilatorRoomId(final Long pInvigilatorRoomId);

  void setInvigilatorRoomName(final String pInvigilatorRoomName);

  void setExamDate(final String pExamDate);

  void setArrivalTime(final String pArrivalTime);
}
