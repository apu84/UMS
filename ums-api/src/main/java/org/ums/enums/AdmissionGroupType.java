package org.ums.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Monjur-E-Morshed on 10-Dec-16.
 */
public enum AdmissionGroupType {
  GENERAL(1, "General Group"),
  FREEDOM_FIGHTER(2, "Freedom Fighter"),
  REMOTE_AREA(3, "Remote Area"),
  ENGLISH_MEDIUM(4, "English Medium Students"),
  COMBINED(5, "GA+FF+RA");

  private String label;
  private int id;

  private AdmissionGroupType(int id, String label) {
    this.id = id;
    this.label = label;
  }

  private static final Map<Integer, AdmissionGroupType> lookup = new HashMap<>();

  static {
    for(AdmissionGroupType c : EnumSet.allOf(AdmissionGroupType.class)) {
      lookup.put(c.getId(), c);
    }
  }

  public static AdmissionGroupType get(final int pId) {
    return lookup.get(pId);
  }

  public String getLabel() {
    return this.label;
  }

  public int getId() {
    return this.id;
  }
}
