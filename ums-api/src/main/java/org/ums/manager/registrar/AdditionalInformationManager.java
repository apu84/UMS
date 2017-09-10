package org.ums.manager.registrar;

import org.ums.employee.additional.AdditionalInformation;
import org.ums.employee.additional.MutableAdditionalInformation;
import org.ums.manager.ContentManager;

public interface AdditionalInformationManager extends
    ContentManager<AdditionalInformation, MutableAdditionalInformation, String> {

  int saveAdditionalInformation(final MutableAdditionalInformation pMutableAdditionalInformation);

  AdditionalInformation getAdditionalInformation(final String pEmployeeId);

  int deleteAdditionalInformation(final MutableAdditionalInformation pMutableAdditionalInformation);

  int updateAdditionalInformation(final MutableAdditionalInformation pMutableAdditionalInformation);
}
