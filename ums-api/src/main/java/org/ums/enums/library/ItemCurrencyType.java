package org.ums.enums.library;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum ItemCurrencyType {
  BDT(1, "BDT"),
  INR(2, "INR"),
  USD(3, "USD"),
  EUR(4, "EUR"),
  GBP(5, "GBP");

  private String label;
  private int id;

  private ItemCurrencyType(int id, String label) {
    this.id = id;
    this.label = label;
  }

  private static final Map<Integer, ItemCurrencyType> lookup = new HashMap<>();

  static {
    for(ItemCurrencyType c : EnumSet.allOf(ItemCurrencyType.class)) {
      lookup.put(c.getId(), c);
    }
  }

  public static ItemCurrencyType get(final int pId) {
    return lookup.get(pId);
  }

  public String getLabel() {
    return this.label;
  }

  public int getId() {
    return this.id;
  }
}
