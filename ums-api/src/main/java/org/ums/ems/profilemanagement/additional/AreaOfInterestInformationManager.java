package org.ums.ems.profilemanagement.additional;

import org.ums.manager.ContentManager;

import java.util.List;

public interface AreaOfInterestInformationManager extends
    ContentManager<AreaOfInterestInformation, MutableAreaOfInterestInformation, String> {

  List<AreaOfInterestInformation> getAll(final String pEmployeeId);

}
