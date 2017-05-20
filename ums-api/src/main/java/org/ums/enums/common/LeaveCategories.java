package org.ums.enums.common;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Monjur-E-Morshed on 18-May-17.
 */
public enum LeaveCategories {
  EARNED_LEAVE_ON_FULL_PAY(1, "EARNED LEAVE ON FULL PAY"),
  EARNED_LEAVE_ON_HALF_PAY(2, "EARNED LEAVE ON HALF PAY"),
  MATERNITY_LEAVE_ON_FULL_PAY(3, "MATERNITY LEAVE ON FULL PAY"),
  QUARANTINE_LEAVE_ON_FULL_PAY(4, "QUARANTINE LEAVE ON FULL PAY"),
  DUTY_LEAVE_ON_FULL_PAY(5, "DUTY LEAVE ON FULL PAY"),
  STUDY_LEAVE_ON_WITHOUT_PAY(6, "STUDY LEAVE ON WITHOUT PAY"),
  EXTRA_ORDINARY_LEAVE_WITH_WITHOUT_PAY(7, "EXTRA-ORDINARY LEAVE WITH WITHOUT PAY"),
  LEAVE_NOT_DUE_ON_HALF_PAY(8, "LEAVE NOT DUE ON HALF PAY"),
  CASUAL_LEAVE_ON_FULL_PAY(9, "CASUAL LEAVE ON FULL PAY"),
  UNKNOWN_ON_FULL_PAY(10, "UNKNOWN ON FULL PAY"),
  UNKNOWN_ON_HALF_PAY(11, "UNKNOWN ON HALF PAY"),
  UNKNOWN_ON_WITHOUT_PAY(12, "UNKNOWN ON WITHOUT PAY");

  private String label;
  private int id;

  private LeaveCategories(int id, String label) {
    this.id = id;
    this.label = label;
  }

  private static final Map<Integer, LeaveCategories> lookup = new HashMap<>();

  static {
    for(LeaveCategories c : EnumSet.allOf(LeaveCategories.class)) {
      lookup.put(c.getId(), c);
    }
  }

  public static LeaveCategories get(final int pId) {
    return lookup.get(pId);
  }

  public String getLabel() {
    return this.label;
  }

  public int getId() {
    return this.id;
  }
}
