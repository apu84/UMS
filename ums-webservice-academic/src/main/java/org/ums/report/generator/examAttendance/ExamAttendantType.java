package org.ums.report.generator.examAttendance;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Monjur-E-Morshed on 7/9/2018.
 */
public enum ExamAttendantType {
  ABSENT(1, "ABSENT"),
  LATE(2, "LATE");

  private String label;
  private int id;

  private ExamAttendantType(int id, String label) {
    this.id = id;
    this.label = label;
  }

  private static final Map<Integer, ExamAttendantType> lookup = new HashMap<>();

  static {
    for(ExamAttendantType c : EnumSet.allOf(ExamAttendantType.class)) {
      lookup.put(c.getId(), c);
    }
  }

  public static ExamAttendantType get(final int pId) {
    return lookup.get(pId);
  }

  public String getLabel() {
    return this.label;
  }

  public int getId() {
    return this.id;
  }
}
