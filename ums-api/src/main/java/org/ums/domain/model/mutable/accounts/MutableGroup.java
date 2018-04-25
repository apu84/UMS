package org.ums.domain.model.mutable.accounts;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.accounts.Group;
import org.ums.domain.model.mutable.MutableLastModifier;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 05-Dec-17.
 */
public interface MutableGroup extends Group, Editable<Long>, MutableLastModifier, MutableIdentifier<Long> {

  void setStringId(Long pId);

  void setCompCode(String pCompanyCode);

  void setGroupCode(String pGroupCode);

  void setDisplayCode(String pDisplayCode);

  void setGroupName(String pGroupName);

  void setMainGroup(String pMainGroup);

  void setReservedFlag(String pReservedFlag);

  void setFlag(String pFlag);

  void setTaxLimit(BigDecimal pTexLimit);

  void setTdsPercent(BigDecimal pTdsPercent);

  void setDefaultComp(String pDefaultCompanyCode);

  void setStatFlag(String pStatusFlag);

  void setStatUpFlag(String pStatusUpFlag);

  void setModifiedDate(Date pLastModifiedDate);

  void setModifiedBy(String pSetModifiedBy);

}
