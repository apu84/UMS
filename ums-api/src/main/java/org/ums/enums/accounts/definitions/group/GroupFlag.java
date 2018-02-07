package org.ums.enums.accounts.definitions.group;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Monjur-E-Morshed on 08-Feb-18.
 */
public enum GroupFlag {
  YES("Y"),
  NO("N");

  private static final Map<String, GroupFlag> Lookup = new HashMap<>();

  static {
    for(GroupFlag c : EnumSet.allOf(GroupFlag.class))
      Lookup.put(c.getValue(), c);
  }

  private String typeValue;

  GroupFlag(String pTypeValue) {
    typeValue = pTypeValue;
  }

  public static GroupFlag get(final String pTypeValue) {
    return Lookup.get(pTypeValue);
  }

  public String getValue() {
    return this.typeValue;
  }
}
