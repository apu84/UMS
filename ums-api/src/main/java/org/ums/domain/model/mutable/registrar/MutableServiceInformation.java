package org.ums.domain.model.mutable.registrar;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.registrar.ServiceInformation;
import org.ums.domain.model.mutable.MutableLastModifier;

public interface MutableServiceInformation extends ServiceInformation, Editable<Integer>, MutableIdentifier<Integer>,
    MutableLastModifier {

  void setEmployeeId(final int pEmployeeId);

  void setEmploymentType(final int pEmploymentType);

  void setDesignation(final int pDesignation);

  void setDeptOffice(final int pDeptOffice);

  void setJoiningDate(final String pJoiningDate);

  void setJobPermanentDate(final String pJobPermanentDate);

  void setJobContractualDate(final String pJobContractualDate);

  void setJobProbationDate(final String pJobProbationDate);

  void setJobTerminationDate(final String pJobTerminationDate);

  void setExtNo(final int pExtNo);

  void setShortName(final String pShortName);

  void setRoomNo(final String pRoomNo);
}
