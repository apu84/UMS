package org.ums.domain.model.mutable.accounts;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.accounts.Group;
import org.ums.domain.model.mutable.MutableLastModifier;

import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 05-Dec-17.
 */
public interface MutableGroup extends Group, Editable<Long>, MutableLastModifier, MutableIdentifier<Long> {

  void setCompanyCode(String pCompanyCode);

  void setGroupCode(String pGroupCode);

  void setGroupName(String pGroupName);

  void setMainGroup(String pMainGroup);

  void setReservedFlag(String pReservedFlag);

  void setFlag(String pFlag);

  void setTexLimit(double pTexLimit);

  void setTdsPercent(double pTdsPercent);

  void setDefaultCompanyCode(String pDefaultCompanyCode);

  void setStatusFlag(char pStatusFlag);

  void setStatusUpFlag(char pStatusUpFlag);

  void setLastModifiedDate(Date pLastModifiedDate);

  void setAuthenticationCode(String pAuthenticationCode);

}
