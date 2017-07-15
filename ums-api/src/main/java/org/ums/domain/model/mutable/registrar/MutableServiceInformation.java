package org.ums.domain.model.mutable.registrar;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.immutable.Designation;
import org.ums.domain.model.immutable.EmploymentType;
import org.ums.domain.model.immutable.registrar.ServiceInformation;
import org.ums.domain.model.mutable.MutableLastModifier;

import java.util.Date;

public interface MutableServiceInformation extends ServiceInformation, Editable<Integer>, MutableIdentifier<Integer>,
    MutableLastModifier {

  void setEmployeeId(final String pEmployeeId);

  void setDepartment(final Department pDepartment);

  void setDepartmentId(final String pDepartmentId);

  void setDesignation(final Designation pDesignation);

  void setDesignationId(final int pDesignationId);

  void setEmployment(final EmploymentType pEmployment);

  void setEmploymentId(final int pEmploymentId);

  void setJoiningDate(final Date pJoiningDate);

  void setResignDate(final Date pResignDate);

  void setRoomNo(final String pRoomNo);

  void setExtNo(final String pExtNo);

  void setAcademicInitial(final String pAcademicInitial);

  void setCurrentStatus(final int pCurrentStatus);
}
