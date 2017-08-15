package org.ums.manager.registrar;

import org.ums.domain.model.immutable.registrar.AcademicInformation;
import org.ums.domain.model.mutable.registrar.MutableAcademicInformation;
import org.ums.manager.ContentManager;

import java.util.List;

public interface AcademicInformationManager extends
    ContentManager<AcademicInformation, MutableAcademicInformation, Long> {

  int saveAcademicInformation(final List<MutableAcademicInformation> pMutableAcademicInformation);

  List<AcademicInformation> getEmployeeAcademicInformation(final String pEmployeeId);

  int deleteAcademicInformation(final String pEmployeeId);

  int updateAcademicInformation(List<MutableAcademicInformation> pMutableAcademicInformation);

  int deleteAcademicInformation(List<MutableAcademicInformation> pMutableAcademicInformation);
}
