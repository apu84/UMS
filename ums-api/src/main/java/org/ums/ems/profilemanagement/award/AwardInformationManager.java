package org.ums.ems.profilemanagement.award;

import org.ums.manager.ContentManager;

import java.util.List;

public interface AwardInformationManager extends ContentManager<AwardInformation, MutableAwardInformation, Long> {

  List<AwardInformation> get(final String pEmployeeId);

  boolean exists(final String pEmployeeId);
}
