package org.ums.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Monjur-E-Morshed on 24-Jan-17.
 */
public enum PaymentMode {

  CASH(1, "Cash"),
  DEMAND_NOTE(2, "Demand note"),
  PAY_ORDER(3, "Pay order");

  private String label;
  private int id;

  private PaymentMode(int id, String label) {
    this.id = id;
    this.label = label;
  }

  private static final Map<Integer, PaymentMode> lookup = new HashMap<>();

  static {
    for(PaymentMode c : EnumSet.allOf(PaymentMode.class)) {
      lookup.put(c.getId(), c);
    }
  }

  public static PaymentMode get(final int pId) {
    return lookup.get(pId);
  }

  public String getLabel() {
    return this.label;
  }

  public int getId() {
    return this.id;
  }
}
