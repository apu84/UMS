package org.ums.employee.additional;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.employee.additional.AdditionalInformation;
import org.ums.employee.additional.MutableAdditionalInformation;
import org.ums.manager.registrar.AdditionalInformationManager;

public class AdditionalInformationDaoDecorator extends
    ContentDaoDecorator<AdditionalInformation, MutableAdditionalInformation, String, AdditionalInformationManager>
    implements AdditionalInformationManager {
  @Override
  public int saveAdditionalInformation(MutableAdditionalInformation pMutableAdditionalInformation) {
    return getManager().saveAdditionalInformation(pMutableAdditionalInformation);
  }

  @Override
  public AdditionalInformation getAdditionalInformation(String pEmployeeId) {
    return getManager().getAdditionalInformation(pEmployeeId);
  }

  @Override
  public int deleteAdditionalInformation(MutableAdditionalInformation pMutableAdditionalInformation) {
    return getManager().deleteAdditionalInformation(pMutableAdditionalInformation);
  }

  @Override
  public int updateAdditionalInformation(MutableAdditionalInformation pMutableAdditionalInformation) {
    return getManager().updateAdditionalInformation(pMutableAdditionalInformation);
  }
}
