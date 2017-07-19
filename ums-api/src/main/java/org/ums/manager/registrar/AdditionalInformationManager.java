package org.ums.manager.registrar;

import org.apache.commons.lang.mutable.Mutable;
import org.ums.domain.model.immutable.registrar.AdditionalInformation;
import org.ums.domain.model.mutable.registrar.MutableAdditionalInformation;
import org.ums.manager.ContentManager;

import java.io.Serializable;
import java.util.List;

public interface AdditionalInformationManager extends
    ContentManager<AdditionalInformation, MutableAdditionalInformation, String> {

  int saveAdditionalInformation(final MutableAdditionalInformation pMutableAdditionalInformation);

  AdditionalInformation getAdditionalInformation(final String pEmployeeId);

  int deleteAdditionalInformation(final MutableAdditionalInformation pMutableAdditionalInformation);

  int updateAdditionalInformation(final MutableAdditionalInformation pMutableAdditionalInformation);
}
