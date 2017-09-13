package org.ums.employee.additional;

import org.ums.employee.additional.AreaOfInterestInformation;
import org.ums.employee.additional.MutableAreaOfInterestInformation;
import org.ums.manager.ContentManager;

import java.util.List;

public interface AreaOfInterestInformationManager extends
    ContentManager<AreaOfInterestInformation, MutableAreaOfInterestInformation, String> {

  List<AreaOfInterestInformation> getAreaOfInterestInformation(final String pEmployeeId);

  int saveAreaOfInterestInformation(final List<MutableAreaOfInterestInformation> pMutableAreaOfInterestInformation);

  int updateAreaOfInformation(final List<MutableAreaOfInterestInformation> pMutableAreaOfInterestInformation);

  int deleteAreaOfInterestInformation(final String pEmployeeId);

}
