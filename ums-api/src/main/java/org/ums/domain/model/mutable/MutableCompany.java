package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Company;

/**
 * Created by Monjur-E-Morshed on 28-Jan-18.
 */
public interface MutableCompany extends Company, Editable<Long>, MutableIdentifier<Long>, MutableLastModifier {

  void setName(String pName);

  void setShortName(String pShortName);
}
