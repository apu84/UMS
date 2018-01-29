package org.ums.domain.model.immutable.accounts;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.mutable.accounts.MutableTransaction;
import org.ums.enums.accounts.definitions.account.balance.BalanceType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 29-Jan-18.
 */
public interface Transaction extends Serializable, EditType<MutableTransaction>, LastModifier, Identifier<Long> {

  Company getCompany();

  String getCompanyId();

  String getDivisionCode();

  String getVoucherNo();

  Date getVoucherDate();

  Integer getSerialNo();

  Account getAccount();

  Long getAccountId();

  Voucher getVoucher();

  Long getVoucherId();

  BigDecimal getAmount();

  BalanceType getBalanceType();

  String getNarration();

  BigDecimal getForeignCurrency();

  Currency getCurrency();

  Long getCurrencyId();

  BigDecimal getConversionFactor();

  String getProjNo();

  Company getDefaultCompany();

  String getDefaultCompanyId();

  String getStatFlag();

  String getStatUpFlag();

  Receipt getReceipt();

  Long getReceiptId();

  Date getPostDate();

  Date getModifiedDate();

  String getModifiedBy();
}
