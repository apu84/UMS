package org.ums.domain.model.immutable.accounts;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.accounts.MutableFinancialAccountYear;
import org.ums.enums.accounts.definitions.financial.account.year.BookClosingFlagType;
import org.ums.enums.accounts.definitions.financial.account.year.YearClosingFlagType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 28-Dec-17.
 */
public interface FinancialAccountYear extends Serializable, LastModifier, EditType<MutableFinancialAccountYear>,
    Identifier<Long> {

  String getStringId();

  Date getCurrentStartDate();

  Date getCurrentEndDate();

  Date getPreviousStartDate();

  Date getPreviousEndDate();

  BookClosingFlagType getBookClosingFlag();

  BigDecimal getItLimit();

  YearClosingFlagType getYearClosingFlag();

  String getStatFlag();

  String getStatUpFlag();

  Date getModifiedDate();

  String getModifiedBy();

}
