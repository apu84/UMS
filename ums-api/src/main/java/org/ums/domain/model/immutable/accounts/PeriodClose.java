package org.ums.domain.model.immutable.accounts;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.accounts.MutablePeriodClose;
import org.ums.enums.accounts.definitions.MonthType;
import org.ums.enums.accounts.definitions.OpenCloseFlag;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 04-Jan-18.
 */
public interface PeriodClose extends Serializable, LastModifier, EditType<MutablePeriodClose>, Identifier<Long> {
  Long getFinAccountYearId();

  MonthType getCloseMonth();

  Integer getCloseYear();

  OpenCloseFlag getPeriodClosingFlag();

  String getStatFlag();

  String getStatUpFlag();

  Date getModifiedDate();

  String getModifiedBy();
}
