package org.ums.enums.common;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum BloodGroupType {
  APositive(1, "A+"),
  ANegative(2, "A-"),
  BPositive(3, "B+"),
  BNegative(4, "B+"),
  ABPositive(5, "AB+"),
  ABNegative(6, "AB-"),
  OPostive(7, "O+"),
  ONegative(8, "O-");

  private String label;
  private Integer id;

  private BloodGroupType(Integer id, String label) {
    this.id = id;
    this.label = label;
  }

  private static final Map<Integer, BloodGroupType> lookup = new HashMap<>();

  static {
    for(BloodGroupType c : EnumSet.allOf(BloodGroupType.class)) {
      lookup.put(c.getId(), c);
    }
  }

  public static BloodGroupType get(final Integer pId) {
    return lookup.get(pId);
  }

  public String getLabel() {
    return this.label;
  }

  public Integer getId() {
    return this.id;
  }
}
