package org.ums.enums.accounts.definitions.currency;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Monjur-E-Morshed on 29-Jan-18.
 */
public enum CurrencyFlag {
  BASE_CURRENCY("B"),
  OTHER_CURRENCY("O");

  private static final Map<String, CurrencyFlag> Lookup = new HashMap<>();

  static {
    for(CurrencyFlag c : EnumSet.allOf(CurrencyFlag.class))
      Lookup.put(c.getValue(), c);
  }

  private String typeValue;

  CurrencyFlag(String pTypeValue) {
    typeValue = pTypeValue;
  }

  public static CurrencyFlag get(final String pTypeValue) {
    return Lookup.get(pTypeValue);
  }

  public String getValue() {
    return this.typeValue;
  }
}
