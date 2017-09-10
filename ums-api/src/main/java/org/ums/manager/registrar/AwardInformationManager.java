package org.ums.manager.registrar;

import org.ums.employee.award.AwardInformation;
import org.ums.employee.award.MutableAwardInformation;
import org.ums.manager.ContentManager;

import java.util.List;

public interface AwardInformationManager extends ContentManager<AwardInformation, MutableAwardInformation, Long> {

  int saveAwardInformation(final List<MutableAwardInformation> pMutableAwardInformation);

  List<AwardInformation> getEmployeeAwardInformation(final String pEmployeeId);

  int deleteAwardInformation(final String pEmployeeId);

  int updateAwardInformation(List<MutableAwardInformation> pAwardInformation);

  int deleteAwardInformation(List<MutableAwardInformation> pAwardInformation);
}
