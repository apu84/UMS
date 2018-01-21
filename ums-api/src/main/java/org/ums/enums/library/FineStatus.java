package org.ums.enums.library;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum FineStatus {

  NO_FINE(0, "No Fine Applied"),
  WAITING_FOR_PAYMENT(1, "Waiting For Payment"),
  FINE_PAID(2, "Payment Clear"),
  FINE_FORGIVEN(3, "Forgiven");

  private String label;
  private int id;

  private FineStatus(int id, String label) {
    this.id = id;
    this.label = label;
  }

  private static final Map<Integer, FineStatus> lookup = new HashMap<>();

  static {
    for(FineStatus c : EnumSet.allOf(FineStatus.class)) {
      lookup.put(c.getId(), c);
    }
  }

  public static FineStatus get(final int pId) {
    return lookup.get(pId);
  }

  public String getLabel() {
    return this.label;
  }

  public int getId() {
    return this.id;
  }
}
