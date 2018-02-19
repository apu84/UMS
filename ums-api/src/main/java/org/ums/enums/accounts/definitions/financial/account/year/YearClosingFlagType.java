package org.ums.enums.accounts.definitions.financial.account.year;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Monjur-E-Morshed on 28-Dec-17.
 */
public enum YearClosingFlagType {
  CLOSED("C"),
  OPEN("O");

  private static final Map<String, YearClosingFlagType> Lookup = new HashMap<>();

  static {
    for(YearClosingFlagType c : EnumSet.allOf(YearClosingFlagType.class))
      Lookup.put(c.getValue(), c);
  }

  private String typeValue;

  YearClosingFlagType(String pTypeValue) {
    typeValue = pTypeValue;
  }

  public static YearClosingFlagType get(final String pTypeValue) {
    return Lookup.get(pTypeValue);
  }

  public String getValue() {
    return this.typeValue;
  }
}
