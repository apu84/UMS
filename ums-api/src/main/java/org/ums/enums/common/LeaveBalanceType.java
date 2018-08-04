package org.ums.enums.common;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Monjur-E-Morshed on 26-Jul-18.
 */
public enum LeaveBalanceType {

  FULL_PAY(1, "FULL PAY"),
  HALF_PAY(2, "HALF PAY");

  private String label;
  private int id;

  private LeaveBalanceType(int id, String label) {
    this.id = id;
    this.label = label;
  }

  private static final Map<Integer, LeaveBalanceType> lookup = new HashMap<>();

  static {
    for(LeaveBalanceType c : EnumSet.allOf(LeaveBalanceType.class)) {
      lookup.put(c.getId(), c);
    }
  }

  public static LeaveBalanceType get(final int pId) {
    return lookup.get(pId);
  }

  public String getLabel() {
    return this.label;
  }

  public int getId() {
    return this.id;
  }

}
