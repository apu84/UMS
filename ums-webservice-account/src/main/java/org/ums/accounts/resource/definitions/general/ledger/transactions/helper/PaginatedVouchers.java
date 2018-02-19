package org.ums.accounts.resource.definitions.general.ledger.transactions.helper;

import org.ums.domain.model.immutable.accounts.AccountTransaction;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 14-Feb-18.
 */
public class PaginatedVouchers {
  private List<AccountTransaction> vouchers;
  private Integer totalNumber;

  public PaginatedVouchers() {}

  public List<AccountTransaction> getVouchers() {
    return vouchers;
  }

  public void setVouchers(List<AccountTransaction> pVouchers) {
    vouchers = pVouchers;
  }

  public Integer getTotalNumber() {
    return totalNumber;
  }

  public void setTotalNumber(Integer pTotalNumber) {
    totalNumber = pTotalNumber;
  }
}
