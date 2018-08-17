package org.ums.employee.additional;

import org.ums.decorator.ContentDaoDecorator;

public class AdditionalInformationDaoDecorator extends
    ContentDaoDecorator<AdditionalInformation, MutableAdditionalInformation, String, AdditionalInformationManager>
    implements AdditionalInformationManager {
  @Override
  public boolean hasDuplicate(String pAcademicInitial, String pDeptId) {
    return getManager().hasDuplicate(pAcademicInitial, pDeptId);
  }
}
