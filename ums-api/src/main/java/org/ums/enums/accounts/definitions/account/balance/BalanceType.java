package org.ums.enums.accounts.definitions.account.balance;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Monjur-E-Morshed on 31-Dec-17.
 */
public enum BalanceType {
  Dr("Dr"),
  Cr("Cr");

  private static final Map<String, BalanceType> Lookup = new HashMap<>();

  static {
    for(BalanceType c : EnumSet.allOf(BalanceType.class))
      Lookup.put(c.getValue(), c);
  }

  private String typeValue;

  BalanceType(String pTypeValue) {
    typeValue = pTypeValue;
  }

  @JsonValue
  public static BalanceType get(final String pTypeValue) {
    return Lookup.get(pTypeValue);
  }

  public String getValue() {
    return this.typeValue;
  }
}
