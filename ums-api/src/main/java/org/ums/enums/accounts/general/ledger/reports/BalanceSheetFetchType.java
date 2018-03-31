package org.ums.enums.accounts.general.ledger.reports;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Monjur-E-Morshed on 29-Mar-18.
 */
public enum BalanceSheetFetchType {
  DETAILED("1"),
  SUMMARIZED("2");

  private static final Map<String, BalanceSheetFetchType> Lookup = new HashMap<>();

  static {
    for(BalanceSheetFetchType c : EnumSet.allOf(BalanceSheetFetchType.class))
      Lookup.put(c.getValue(), c);
  }

  private String typeValue;

  BalanceSheetFetchType(String pTypeValue) {
    typeValue = pTypeValue;
  }

  public static BalanceSheetFetchType get(final String pTypeValue) {
    return Lookup.get(pTypeValue);
  }

  public String getValue() {
    return this.typeValue;
  }
}
