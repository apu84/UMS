package org.ums.employee.additional;

import org.ums.manager.ContentManager;

public interface AdditionalInformationManager extends
    ContentManager<AdditionalInformation, MutableAdditionalInformation, String> {

  boolean hasDuplicate(final String pAcademicInitial, final String pDeptId);
}
