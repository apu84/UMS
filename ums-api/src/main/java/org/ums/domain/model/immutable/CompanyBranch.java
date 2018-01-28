package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableCompanyBranch;

import java.io.Serializable;

/**
 * Created by Monjur-E-Morshed on 28-Jan-18.
 */
public interface CompanyBranch extends Serializable, EditType<MutableCompanyBranch>, LastModifier, Identifier<Long> {

  Company getCompany();

  Long getCompanyId();

  String getName();
}
