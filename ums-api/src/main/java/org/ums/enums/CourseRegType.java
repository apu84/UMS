package org.ums.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ikh on 6/16/2016.
 */
public enum CourseRegType {

  REGULAR(1, "Regular"), CLEARANCE(2, "Clearance"), CARRY(3, "Carry"), SPECIAL_CARRY(4,
      "Special Carry"), IMPROVEMENT(5, "Improvement");

  private String label;
  private int id;

  private CourseRegType(int id, String label) {
    this.id = id;
    this.label = label;
  }

  private static final Map<Integer, CourseRegType> lookup = new HashMap<>();

  static {
    for(CourseRegType c : EnumSet.allOf(CourseRegType.class)) {
      lookup.put(c.getId(), c);
    }
  }

  public static CourseRegType get(final int pId) {
    return lookup.get(pId);
  }

  public String getLabel() {
    return this.label;
  }

  public int getId() {
    return this.id;
  }

  public static void main(String args[]) {

  }

}
