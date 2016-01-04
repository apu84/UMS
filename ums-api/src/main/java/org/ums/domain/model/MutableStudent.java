package org.ums.domain.model;

import java.util.Date;

public interface MutableStudent extends Student, Mutable, MutableIdentifier<String>, MutableLastModifier {
  void setUser(final User pUser);

  void setFullName(final String pFullName);

  void setDepartmentId(final Integer pDepartmentId);

  void setDepartment(final Department pDepartment) throws Exception;

  void setSemesterId(final Integer pSemesterId);

  void setSemester(final Semester pSemester) throws Exception;

  void setProgramId(final Integer pProgramId);

  void setProgram(final Program pProgram) throws Exception;

  void setFatherName(final String pFatherName);

  void setMotherName(final String pMotherName);

  void setDateOfBirth(final Date pDateOfBirth);

  void setGender(final String pGender);

  void setPresentAddress(final String pPresentAddress);

  void setPermanentAddress(final String pPermanentAddress);

  void setMobileNo(final String pMobileNo);

  void setPhoneNo(final String pPhoneNo);

  void setBloodGroup(final String pBloodGroup);

  void setEmail(final String pEmail);

  void setGuardianName(final String pGuardianName);

  void setGuardianMobileNo(final String pGuardianMobileNo);

  void setGuardianPhoneNo(final String pGuardianPhoneNo);

  void setGuardianEmail(final String pGuardianEmail);
}
