package org.ums.enums.accounts.definitions.financial.account.year;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Monjur-E-Morshed on 28-Dec-17.
 */
public enum BookClosingFlagType {
  CLOSED("C"),
  OPEN("O");

  private static final Map<String, BookClosingFlagType> Lookup = new HashMap<>();

  static {
    for(BookClosingFlagType c : EnumSet.allOf(BookClosingFlagType.class))
      Lookup.put(c.getValue(), c);
  }

  private String typeValue;

  BookClosingFlagType(String pTypeValue) {
    typeValue = pTypeValue;
  }

  public static BookClosingFlagType get(final String pTypeValue) {
    return Lookup.get(pTypeValue);
  }

  public String getValue() {
    return this.typeValue;
  }
}
