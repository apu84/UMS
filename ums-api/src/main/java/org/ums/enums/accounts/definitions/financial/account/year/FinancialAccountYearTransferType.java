package org.ums.enums.accounts.definitions.financial.account.year;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum FinancialAccountYearTransferType {
  ALL_ACCOUNTS_CLOSING(1),
  BANK_AND_CASH_CLOSING(2),
  TRANSFER_WITHOUT_CLOSING(3);

  private static final Map<Integer, FinancialAccountYearTransferType> Lookup = new HashMap<>();

  static {
    for(FinancialAccountYearTransferType c : EnumSet.allOf(FinancialAccountYearTransferType.class))
      Lookup.put(c.getValue(), c);
  }

  private Integer typeValue;

  FinancialAccountYearTransferType(Integer pTypeValue) {
    typeValue = pTypeValue;
  }

  public static FinancialAccountYearTransferType get(final Integer pTypeValue) {
    return Lookup.get(pTypeValue);
  }

  public Integer getValue() {
    return this.typeValue;
  }
}
