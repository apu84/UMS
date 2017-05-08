package org.ums.domain.model.mutable.common;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.common.LmsType;
import org.ums.domain.model.mutable.MutableLastModifier;
import org.ums.enums.common.DurationType;
import org.ums.enums.common.EmployeeLeaveType;
import org.ums.enums.common.SalaryType;

/**
 * Created by Monjur-E-Morshed on 03-May-17.
 */
public interface MutableLmsType extends LmsType, Editable<Integer>, MutableLastModifier, MutableIdentifier<Integer> {

  void setName(String pName);

  void setType(EmployeeLeaveType pType);

  void setDuration(int pDuration);

  void setMaxDuration(int pMaxDuration);

  void setMaxSimultaneousDuration(int pMaxSimultaneousDuration);

  void setDurationType(DurationType pDurationType);

  void setSalaryType(SalaryType pSalaryType);

  void setAuthorizeRoleId(int pAuthorizeRoleId);

  void setSpecialAuthorizeRoleId(int pSpecialAuthorizeRoleId);
}
