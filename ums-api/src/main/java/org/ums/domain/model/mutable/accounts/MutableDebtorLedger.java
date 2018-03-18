package org.ums.domain.model.mutable.accounts;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.accounts.AccountTransaction;
import org.ums.domain.model.immutable.accounts.DebtorLedger;
import org.ums.domain.model.mutable.MutableLastModifier;
import org.ums.enums.accounts.definitions.account.balance.BalanceType;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 11-Mar-18.
 */
public interface MutableDebtorLedger extends DebtorLedger, Editable<Long>, MutableIdentifier<Long>, MutableLastModifier {

  void setCompany(Company pCompany);

  void setCompanyId(String pCompanyId);

  void setDivisionCode(String pDivisionCode);

  void setCustomerCode(String pCustomerCode);

  void setAccountTransaction(AccountTransaction pAccountTransaction);

  void setAccountTransactionId(Long pAccountTransactionId);

  void setVoucherNo(String pVoucherNo);

  void setVoucherDate(Date pVoucherDate);

  void setSerialNo(Integer pSerialNo);

  void setInvoiceNo(String pInvoiceNo);

  void setInvoiceDate(Date pInvoiceDate);

  void setAmount(BigDecimal pAmount);

  void setPaidAmount(BigDecimal pPaidAmount);

  void setDueDate(Date pDueDate);

  void setBalanceType(BalanceType pBalanceType);

  void setInvoiceClosingFlag(String pInvoiceClosingFlag);

  void setCurrencyCode(String pCurrencyCode);

  void setStatFlag(String pStatFlag);

  void setStatUpFlag(String pStatUpFlag);

  void setModificationDate(Date pModificationDate);

  void setModifiedBy(String pModifiedBy);
}
