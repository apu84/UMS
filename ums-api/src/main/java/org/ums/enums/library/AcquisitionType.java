package org.ums.enums.library;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ifti on 16-Feb-17.
 */
public enum AcquisitionType {
  PURCHASE(1, "Purchase"),
  DONATION(2, "Donation'");

  private String label;
  private int id;

  private AcquisitionType(int id, String label) {
    this.id = id;
    this.label = label;
  }

  private static final Map<Integer, AcquisitionType> lookup = new HashMap<>();

  static {
    for(AcquisitionType c : EnumSet.allOf(AcquisitionType.class)) {
      lookup.put(c.getId(), c);
    }
  }

  public static AcquisitionType get(final int pId) {
    return lookup.get(pId);
  }

  public String getLabel() {
    return this.label;
  }

  public int getId() {
    return this.id;
  }
}
