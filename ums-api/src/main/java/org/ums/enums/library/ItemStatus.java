package org.ums.enums.library;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ifti on 04-Mar-17.
 */
public enum ItemStatus {
  ENTRY_MODE(0, "Entry Mode"),
  AVAILABLE(2, "Available");

  private String label;
  private int id;

  private ItemStatus(int id, String label) {
    this.id = id;
    this.label = label;
  }

  private static final Map<Integer, ItemStatus> lookup = new HashMap<>();

  static {
    for(ItemStatus c : EnumSet.allOf(ItemStatus.class)) {
      lookup.put(c.getId(), c);
    }
  }

  public static ItemStatus get(final int pId) {
    return lookup.get(pId);
  }

  public String getLabel() {
    return this.label;
  }

  public int getId() {
    return this.id;
  }
}
