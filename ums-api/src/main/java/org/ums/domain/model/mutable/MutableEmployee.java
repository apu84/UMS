package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.immutable.Employee;

import java.util.Date;

public interface MutableEmployee extends Employee, Editable<String>, MutableLastModifier,
    MutableIdentifier<String> {

  void setEmployeeName(final String pEmployeeName);

  void setDesignation(final int pDesignation);

  void setEmploymentType(final String pEmploymentType);

  void setDepartment(final Department pDepartment);

  void setFatherName(final String pFatherName);

  void setMotherName(final String pMotherName);

  void setBirthDate(final Date pBirthDate);

  void setGender(final String pGender);

  void setBloodGroup(final String pBloodGroup);

  void setPresentAddress(final String pPresentAddress);

  void setPermanentAddress(final String pPermanentAddress);

  void setMobileNumber(final String pMobileNumber);

  void setPhoneNumber(final String pPhoneNumber);

  void setEmailAddress(final String pEmailAddress);

  void setJoiningDate(final Date pJoiningDate);

  void setJobParmanentDate(final Date pJobParmanentDate);

  void setStatus(final int pStatus);

  void setShortName(final String pShortName);
}
