package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.AbsLateComingInfo;

/**
 * Created by Monjur-E-Morshed on 7/1/2018.
 */
public interface MutableAbsLateComingInfo extends AbsLateComingInfo, Editable<Long>, MutableLastModifier,
        MutableIdentifier<Long> {
    void  setId(final Long pId);

    void setSemesterId(final Integer pSemesterId);

    void setExamType(final Integer pSetExamType);

    void setPresentType(final Integer pPresentType);

    void setPresentInfo(final String  pPresentInfo);

    void setRemarks(final String pRemarks);

    void setEmployeeId(final String pEmployeeId);

    void setEmployeeName();

    void setEmployeeType();

    void setDeptId();

    void setDeptName();

    void setInvigilatorRoomId();

    void setInvigilatorRoomName();

    void setExamDate();

   void setArrivalTime();
}
