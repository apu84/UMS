package org.ums.employee.academic;

import org.ums.manager.ContentManager;

import java.util.List;

public interface AcademicInformationManager extends
    ContentManager<AcademicInformation, MutableAcademicInformation, Long> {

  List<AcademicInformation> get(final String pEmployeeId);

  boolean exists(final String pEmployeeId);
}
