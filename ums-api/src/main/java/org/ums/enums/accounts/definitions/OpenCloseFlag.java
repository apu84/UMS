package org.ums.enums.accounts.definitions;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Monjur-E-Morshed on 04-Jan-18.
 */
public enum OpenCloseFlag {
  O("O"),
  C("C");

  private static final Map<String, OpenCloseFlag> Lookup = new HashMap<>();

  static {
    for(OpenCloseFlag m : EnumSet.allOf(OpenCloseFlag.class))
      Lookup.put(m.getValue(), m);
  }

  private String typeValue;

  OpenCloseFlag(String pTypeValue) {
    typeValue = pTypeValue;
  }

  public static OpenCloseFlag get(final String pTypeValue) {
    return Lookup.get(pTypeValue);
  }

  public String getValue() {
    return this.typeValue;
  }
}
