package org.ums.employee.publication;

import org.ums.manager.ContentManager;

import java.util.List;

public interface PublicationInformationManager extends
    ContentManager<PublicationInformation, MutablePublicationInformation, Long> {

  List<PublicationInformation> get(final String pEmployeeId);

  boolean exists(final String pEmployeeId);
}
