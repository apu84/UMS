package org.ums.manager.registrar;

import org.ums.domain.model.immutable.registrar.AwardInformation;
import org.ums.domain.model.mutable.registrar.MutableAwardInformation;
import org.ums.manager.ContentManager;

import java.util.List;

public interface AwardInformationManager extends ContentManager<AwardInformation, MutableAwardInformation, Integer> {

  int saveAwardInformation(final List<MutableAwardInformation> pMutableAwardInformation);

  List<AwardInformation> getEmployeeAwardInformation(final String pEmployeeId);
}
