package org.ums.enums.accounts.general.ledger.reports;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Monjur-E-Morshed on 28-Mar-18.
 */
public enum FetchType {
  ALL("1"),
  TRANSACTION_SPECIFIC("2");

  private static final Map<String, FetchType> Lookup = new HashMap<>();

  static {
    for(FetchType c : EnumSet.allOf(FetchType.class))
      Lookup.put(c.getValue(), c);
  }

  private String typeValue;

  FetchType(String pTypeValue) {
    typeValue = pTypeValue;
  }

  public static FetchType get(final String pTypeValue) {
    return Lookup.get(pTypeValue);
  }

  public String getValue() {
    return this.typeValue;
  }
}
