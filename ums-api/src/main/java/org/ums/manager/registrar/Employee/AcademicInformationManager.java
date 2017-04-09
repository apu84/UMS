package org.ums.manager.registrar.Employee;

import org.ums.domain.model.immutable.registrar.Employee.AcademicInformation;
import org.ums.domain.model.mutable.registrar.Employee.MutableAcademicInformation;
import org.ums.manager.ContentManager;

import java.util.List;

public interface AcademicInformationManager extends
    ContentManager<AcademicInformation, MutableAcademicInformation, Integer> {

  int saveAcademicInformation(final MutableAcademicInformation pMutableAcademicInformation);

  List<AcademicInformation> getEmployeeAcademicInformation(final int pEmployeeId);
}
