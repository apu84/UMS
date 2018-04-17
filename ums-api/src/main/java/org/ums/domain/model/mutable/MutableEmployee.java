package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.immutable.Designation;
import org.ums.domain.model.immutable.Employee;
import org.ums.employee.personal.PersonalInformation;

import java.util.Date;

public interface MutableEmployee extends Employee, Editable<String>, MutableLastModifier, MutableIdentifier<String> {

  void setDesignationId(final int pDesignationId);

  void setDesignation(final Designation pDesignation);

  void setEmploymentType(final String pEmploymentType);

  void setDepartment(final Department pDepartment);

  void setJoiningDate(final Date pJoiningDate);

  void setStatus(final int pStatus);

  void setShortName(final String pShortName);

  void setEmployeeType(final int pEmployeeType);

  void setPersonalInformation(final PersonalInformation pPersonalInformation);
}
