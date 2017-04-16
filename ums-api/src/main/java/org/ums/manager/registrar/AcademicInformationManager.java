package org.ums.manager.registrar;

import org.ums.domain.model.immutable.registrar.AcademicInformation;
import org.ums.domain.model.mutable.registrar.MutableAcademicInformation;
import org.ums.manager.ContentManager;

import java.util.List;

public interface AcademicInformationManager extends
    ContentManager<AcademicInformation, MutableAcademicInformation, Integer> {

  int saveAcademicInformation(final List<MutableAcademicInformation> pMutableAcademicInformation);

  List<AcademicInformation> getEmployeeAcademicInformation(final int pEmployeeId);
}
