package org.ums.usermanagement.userView;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.mutable.MutableLastModifier;
import org.ums.usermanagement.role.Role;

import java.util.Date;

public interface MutableUserView extends UserView, Editable<String>, MutableIdentifier<String>, MutableLastModifier {
  void setUserName(final String pUserName);

  void setGender(final String pGender);

  void setDateOfBirth(final Date pDateOfBirth);

  void setBloodGroup(final String pBloodGroup);

  void setFatherName(final String pFatherName);

  void setMotherName(final String pMotherName);

  void setMobileNumber(final String pMobileNumber);

  void setEmailAddress(final String pEmailAddress);

  void setDepartment(final String pDepartment);

  void setDesignation(final int pDesignation);

  void setRoleId(final int pRoleId);

  void setStatus(final int pStatus);
}
