package org.ums.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by My Pc on 02-Oct-16.
 */
public enum StudentStatus {
  PASSED(0, "Passed"),
  ACTIVE(1, "Active"),
  MIGRATED(2, "Migrated"),
  CANCELLED(3, "Cancelled");

  private String label;
  private int id;

  private StudentStatus(int id, String label) {
    this.id = id;
    this.label = label;
  }

  private static final Map<Integer, StudentStatus> lookup = new HashMap<>();

  static {
    for(StudentStatus c : EnumSet.allOf(StudentStatus.class)) {
      lookup.put(c.getId(), c);
    }
  }

  public static StudentStatus get(final int pId) {
    return lookup.get(pId);
  }

  public String getLabel() {
    return this.label;
  }

  public int getId() {
    return this.id;
  }
}
