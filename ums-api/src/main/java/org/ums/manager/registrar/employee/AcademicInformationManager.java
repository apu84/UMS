package org.ums.manager.registrar.employee;

import org.ums.domain.model.immutable.registrar.employee.AcademicInformation;
import org.ums.domain.model.mutable.registrar.employee.MutableAcademicInformation;
import org.ums.manager.ContentManager;

import java.util.List;

public interface AcademicInformationManager extends
    ContentManager<AcademicInformation, MutableAcademicInformation, Integer> {

  int saveAcademicInformation(final MutableAcademicInformation pMutableAcademicInformation);

  List<AcademicInformation> getEmployeeAcademicInformation(final int pEmployeeId);
}
