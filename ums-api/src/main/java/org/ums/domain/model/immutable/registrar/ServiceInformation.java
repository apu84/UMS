package org.ums.domain.model.immutable.registrar;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.registrar.MutableServiceInformation;

import java.io.Serializable;

public interface ServiceInformation extends Serializable, EditType<MutableServiceInformation>, Identifier<Integer>,
    LastModifier {

  int getEmployeeId();

  int getEmploymentType();

  int getDesignation();

  int getDeptOffice();

  String getJoiningDate();

  String getJobPermanentDate();

  String getJobContractualDate();

  String getJobProbationDate();

  String getJobTerminationDate();

  int getExtNo();

  String getShortName();

  String getRoomNo();
}
