package org.ums.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ikh on 4/29/2016.
 */

public enum ExamType {

  SEMESTER_FINAL(1, "Semester Final"),
  CLEARANCE_CARRY_IMPROVEMENT(2, "Carry/Clearance/Improvement"),
  SEMESTER_FINAL_CIVIL_SPECIAL(3, "Civil Final");

  private String label;
  private int id;

  private ExamType(int id, String label) {
    this.id = id;
    this.label = label;
  }

  private static final Map<Integer, ExamType> lookup = new HashMap<>();

  static {
    for(ExamType c : EnumSet.allOf(ExamType.class)) {
      lookup.put(c.getId(), c);
    }
  }

  public static ExamType get(final int pId) {
    return lookup.get(pId);
  }

  public String getLabel() {
    return this.label;
  }

  public int getId() {
    return this.id;
  }

}
