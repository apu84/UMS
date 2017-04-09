package org.ums.domain.model.mutable.registrar.Employee;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.registrar.Employee.EmployeeInformation;
import org.ums.domain.model.mutable.MutableLastModifier;

import java.util.Date;

public interface MutableEmployeeInformation extends EmployeeInformation, Editable<Integer>, MutableIdentifier<Integer>,
    MutableLastModifier {

  void setEmployeeId(final int pEmployeeId);

  void setEmploymentType(final String pEmploymentType);

  void setDesignation(final int pDesignation);

  void setDeptOffice(final String pDeptOffice);

  void setJoiningDate(final String pJoiningDate);

  void setJobPermanentDate(final String pJobPermanentDate);

  void setJobTerminationDate(final String pJobTerminationDate);

  void setExtNo(final int pExtNo);

  void setShortName(final String pShortName);

  void setRoomNo(final String pRoomNo);
}
