package org.ums.manager.registrar;

import org.ums.domain.model.immutable.registrar.AreaOfInterestInformation;
import org.ums.domain.model.mutable.registrar.MutableAreaOfInterestInformation;
import org.ums.manager.ContentManager;

import java.util.List;

public interface AreaOfInterestInformationManager extends
    ContentManager<AreaOfInterestInformation, MutableAreaOfInterestInformation, String> {

  List<AreaOfInterestInformation> getAreaOfInterestInformation(final String pEmployeeId);

  int saveAreaOfInterestInformation(final List<MutableAreaOfInterestInformation> pMutableAreaOfInterestInformation);

  int updateAreaOfInformation(final List<MutableAreaOfInterestInformation> pMutableAreaOfInterestInformation);

  int deleteAreaOfInterestInformation(final String pEmployeeId);

}
