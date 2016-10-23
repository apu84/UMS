package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.immutable.Employee;

/**
 * Created by My Pc on 3/30/2016.
 */
public interface MutableEmployee extends Employee, Mutable, MutableLastModifier,
    MutableIdentifier<String> {

  void setEmployeeName(final String pEmployeeName);

  void setDesignation(final int pDesignation);

  void setEmploymentType(final String pEmploymentType);

  void setDepartment(final Department pDepartment);

  void setFatherName(final String pFatherName);

  void setMotherName(final String pMotherName);

  void setBirthDate(final String pBirthDate);

  void setGender(final char pGender);

  void setBloodGroup(final String pBloodGroup);

  void setPresentAddress(final String pPresentAddress);

  void setPermanentAddress(final String pPermanentAddress);

  void setMobileNumber(final String pMobileNumber);

  void setPhoneNumber(final String pPhoneNumber);

  void setEmailAddress(final String pEmailAddress);

  void setJoiningDate(final String pJoiningDate);

  void setJobParmanentDate(final String pJobParmanentDate);

  void setStatus(final int pStatus);

}
