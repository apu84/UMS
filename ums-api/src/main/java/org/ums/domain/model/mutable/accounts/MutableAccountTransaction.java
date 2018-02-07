package org.ums.domain.model.mutable.accounts;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.accounts.*;
import org.ums.domain.model.mutable.MutableLastModifier;
import org.ums.enums.accounts.definitions.account.balance.BalanceType;
import org.ums.enums.accounts.general.ledger.vouchers.AccountTransactionType;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 29-Jan-18.
 */
public interface MutableAccountTransaction extends AccountTransaction, Editable<Long>, MutableIdentifier<Long>,
    MutableLastModifier {

  void setCompany(Company pCompany);

  void setCompanyId(String pCompanyId);

  void setDivisionCode(String pDivisionCode);

  void setVoucherNo(String pVoucherNo);

  void setVoucherDate(Date pVoucherDate);

  void setSerialNo(Integer pSerialNo);

  void setAccount(Account pAccount);

  void setAccountId(Long pAccountId);

  void setVoucher(Voucher pVoucher);

  void setVoucherId(Long pVoucherId);

  void setAmount(BigDecimal pAmount);

  void setBalanceType(BalanceType pBalanceType);

  void setNarration(String pNarration);

  void setForeignCurrency(BigDecimal pForeignCurrency);

  void setCurrency(Currency pCurrency);

  void setCurrencyId(Long pCurrencyId);

  void setConversionFactor(BigDecimal pConversionFactor);

  void setProjNo(String pProjNo);

  void setDefaultCompany(Company pDefaultCompany);

  void setDefaultCompanyId(String pDefaultCompanyId);

  void setStatFlag(String pStatFlag);

  void setStatUpFlag(String pStatUpFlag);

  void setReceipt(Receipt pReceipt);

  void setReceiptId(Long pReceiptId);

  void setPostDate(Date pPostDate);

  void setAccountTransactionType(AccountTransactionType pAccountTransactionType);

  void setModifiedDate(Date pModifiedDate);

  void setModifiedBy(String pModifiedBy);
}
