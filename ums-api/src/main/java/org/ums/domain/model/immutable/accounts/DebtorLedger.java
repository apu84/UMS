package org.ums.domain.model.immutable.accounts;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.mutable.accounts.MutableDebtorLedger;
import org.ums.enums.accounts.definitions.account.balance.BalanceType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 11-Mar-18.
 */
public interface DebtorLedger extends Serializable, EditType<MutableDebtorLedger>, LastModifier, Identifier<Long> {

  Company getCompany();

  String getCompanyId();

  String getDivisionCode();

  Date getCustomerCode();

  AccountTransaction getAccountTransaction();

  Long getAccountTransactionId();

  String getVoucherNo();

  Date getVoucherDate();

  Integer getSerialNo();

  String getInvoiceNo();

  String getInvoiceDate();

  BigDecimal getAmount();

  BigDecimal getPaidAmount();

  Date getDueDate();

  BalanceType getBalanceType();

  String getInvoiceClosingFlag();

  String getCurrencyCode();

  String getStatFlag();

  String getStatUpFlag();

  Date getModificationDate();

  String getModifiedBy();
}
