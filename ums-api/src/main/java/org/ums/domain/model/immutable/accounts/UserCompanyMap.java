package org.ums.domain.model.immutable.accounts;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.mutable.MutableCompany;
import org.ums.domain.model.mutable.MutableUserCompanyMap;

import java.io.Serializable;
import java.util.List;

public interface UserCompanyMap extends Serializable, EditType<MutableUserCompanyMap>, LastModifier, Identifier<Long> {

  String getUserId();

  String getCompanyId();

}
