package org.ums.domain.model.immutable.accounts;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.mutable.accounts.MutableSystemGroupMap;
import org.ums.enums.accounts.definitions.group.GroupType;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 26-Apr-18.
 */
public interface SystemGroupMap extends Serializable, EditType<MutableSystemGroupMap>, LastModifier, Identifier<Long> {

  GroupType getGroupType();

  Group getGroup();

  Long getGroupId();

  Company getCompany();

  String getCompanyId();

  String getModifiedBy();

  String getModifierName();

  Date getModifiedDate();
}
