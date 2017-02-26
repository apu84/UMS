package org.ums.enums.academic;

import org.ums.enums.ExamType;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ifti on 26-Feb-17.
 */
public enum GradeSubmissionColorCode {

  NONE(0, "None"),
  SUBMITTED(1, "Submitted"),
  TIMEOVER(9, "TimeOver");

  private String label;
  private int id;

  private GradeSubmissionColorCode(int id, String label) {
    this.id = id;
    this.label = label;
  }

  private static final Map<Integer, GradeSubmissionColorCode> lookup = new HashMap<>();

  static {
    for(GradeSubmissionColorCode c : EnumSet.allOf(GradeSubmissionColorCode.class)) {
      lookup.put(c.getId(), c);
    }
  }

  public static GradeSubmissionColorCode get(final int pId) {
    return lookup.get(pId);
  }

  public String getLabel() {
    return this.label;
  }

  public int getId() {
    return this.id;
  }
}
