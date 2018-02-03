package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.CompanyBranch;

/**
 * Created by Monjur-E-Morshed on 28-Jan-18.
 */
public interface MutableCompanyBranch extends CompanyBranch, Editable<Long>, MutableIdentifier<Long>,
    MutableLastModifier {

  void setCompany(Company pCompany);

  void setCompanyId(String pCompanyId);

  void setName(String pName);
}
