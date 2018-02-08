package org.ums.accounts.resource.definitions.general.ledger.transactions;

import io.reactivex.internal.operators.parallel.ParallelJoin;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.accounts.AccountTransaction;
import org.ums.domain.model.mutable.accounts.MutableAccountTransaction;
import org.ums.enums.accounts.definitions.account.balance.BalanceType;
import org.ums.enums.accounts.general.ledger.vouchers.AccountTransactionType;
import org.ums.util.UmsUtils;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;
import java.math.BigDecimal;

/**
 * Created by Monjur-E-Morshed on 31-Jan-18.
 */
@Component
public class AccountTransactionBuilder implements Builder<AccountTransaction, MutableAccountTransaction> {
  @Override
  public void build(JsonObjectBuilder pBuilder, AccountTransaction pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {

  }

  @Override
  public void build(MutableAccountTransaction pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

  }

  public void build(MutableAccountTransaction pMutableAccountTransaction, JsonObject pJsonObject) throws Exception {
    if(pJsonObject.containsKey("id"))
      pMutableAccountTransaction.setId(Long.parseLong(pJsonObject.getString("id")));
    if(pJsonObject.containsKey("companyId"))
      pMutableAccountTransaction.setCompanyId(pJsonObject.getString("companyId"));
    if(pJsonObject.containsKey("divisionCode"))
      pMutableAccountTransaction.setDivisionCode(pJsonObject.getString("divisionCode"));
    if(pJsonObject.containsKey("voucherNo"))
      pMutableAccountTransaction.setVoucherNo(pJsonObject.getString("voucherNo"));
    if(pJsonObject.containsKey("voucherDate"))
      pMutableAccountTransaction.setVoucherDate(UmsUtils.convertToDate(pJsonObject.getString("voucherDate"),
          "dd-MM-yyyy"));
    if(pJsonObject.containsKey("serialNo"))
      pMutableAccountTransaction.setSerialNo(pJsonObject.getInt("serialNo"));
    if(pJsonObject.containsKey("accountId"))
      pMutableAccountTransaction.setAccountId(Long.parseLong(pJsonObject.getString("accountId")));
    if(pJsonObject.containsKey("voucherId"))
      pMutableAccountTransaction.setVoucherId(Long.parseLong(pJsonObject.getString("voucherId")));
    if(pJsonObject.containsKey("amount"))
      pMutableAccountTransaction.setAmount(BigDecimal.valueOf(Double.parseDouble(pJsonObject.getJsonNumber("amount")
          .toString())));
    if(pJsonObject.containsKey("balanceType"))
      pMutableAccountTransaction.setBalanceType(BalanceType.get(pJsonObject.getString("balanceType")));
    if(pJsonObject.containsKey("narration"))
      pMutableAccountTransaction.setNarration(pJsonObject.getString("narration"));
    if(pJsonObject.containsKey("foreignCurrency"))
      pMutableAccountTransaction.setForeignCurrency(BigDecimal.valueOf(Double.parseDouble(pJsonObject.getJsonNumber(
          "foreignCurrency").toString())));
    if(pJsonObject.containsKey("currencyId"))
      pMutableAccountTransaction.setCurrencyId(Long.parseLong(pJsonObject.getString("currencyId")));
    if(pJsonObject.containsKey("conversionFactor"))
      pMutableAccountTransaction.setConversionFactor(BigDecimal.valueOf(Double.parseDouble(pJsonObject.getJsonNumber(
          "conversionFactor").toString())));
    if(pJsonObject.containsKey("receiptId"))
      pMutableAccountTransaction.setReceiptId(Long.parseLong("receiptId"));
    if(pJsonObject.containsKey("postDate"))
      pMutableAccountTransaction.setPostDate(UmsUtils.convertToDate(pJsonObject.getString("postDate"), "dd-MM-yyyy"));
    if(pJsonObject.containsKey("accountTransactionType"))
      pMutableAccountTransaction.setAccountTransactionType(AccountTransactionType.get(pJsonObject
          .getString("accountTransactionType")));
    if(pJsonObject.containsKey("modifiedDate"))
      pMutableAccountTransaction.setModifiedDate(UmsUtils.convertToDate(pJsonObject.getString("modifiedDate"),
          "dd-MM-yyyy"));
    if(pJsonObject.containsKey("modifiedBy"))
      pMutableAccountTransaction.setModifiedBy(pJsonObject.getString("modifiedBy"));
  }
}
