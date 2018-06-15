package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.accounts.UserCompanyMap;

import java.util.List;

public interface MutableUserCompanyMap extends UserCompanyMap, Editable<String>, MutableIdentifier<Long>,
    MutableLastModifier {

  void setUserId(String pUserId);

  void setCompanyId(String pCompanyId);
}
