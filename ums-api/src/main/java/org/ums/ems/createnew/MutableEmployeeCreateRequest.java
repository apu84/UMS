package org.ums.ems.createnew;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.mutable.MutableLastModifier;

import java.util.Date;

public interface MutableEmployeeCreateRequest extends EmployeeCreateRequest, Editable<String>, MutableLastModifier,
    MutableIdentifier<String> {

  void setEmail(final String pEmail);

  void setSalutation(final Integer pSalutation);

  void setEmployeeName(final String pEmployeeName);

  void setAcademicInitial(final String pAcademicInitial);

  void setDepartmentId(final String pDepartmentId);

  void setEmployeeType(final Integer pEmployeeType);

  void setDesignation(final Integer pDesignation);

  void setEmploymentType(final Integer pEmploymentType);

  void setJoiningDate(final Date pJoiningDate);

  void setCreateAccount(final Integer pCreateAccount);

  void setRequestedBy(final String pRequestedBy);

  void setRequestedOn(final Date pRequestedOn);

  void setActionTakenBy(final String pActionTakenBy);

  void setActionTakenOn(final Date pActionTakenOn);

  void setActionStatus(final Integer pActionStatus);
}
