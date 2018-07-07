package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableAbsLateComingInfo;

import java.io.Serializable;

/**
 * Created by Monjur-E-Morshed on 7/1/2018.
 */
public interface AbsLateComingInfo extends Serializable, LastModifier, EditType<MutableAbsLateComingInfo>,
    Identifier<Long> {

  Long getId();

  Integer getSemesterId();

  Integer getExamType();

  Integer getPresentType();

  String getPresentInfo();

  String getRemarks();

  String getEmployeeId();

  String getEmployeeName();

  String getEmployeeType();

  String getDeptId();

  String getDeptName();

  Integer getInvigilatorRoomId();

  String getInvigilatorRoomName();

  String getExamDate();

  String getArrivalTime();

}
