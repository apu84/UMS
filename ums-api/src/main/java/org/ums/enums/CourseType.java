package org.ums.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ifti on 08-Jan-16.
 */
public enum CourseType {
  THEORY(1, "Theory"), SESSIONAL(2, "Sessional"), THESIS_PROJECT(3, "Thesis-Project"), NONE(0, "");

  private String label;
  private int id;

  private CourseType(int id, String label) {
    this.id = id;
    this.label = label;
  }

  private static final Map<Integer, CourseType> lookup = new HashMap<>();

  static {
    for(CourseType c : EnumSet.allOf(CourseType.class)) {
      lookup.put(c.getId(), c);
    }
  }

  public static CourseType get(final int pId) {
    return lookup.get(pId);
  }

  public String getLabel() {
    return this.label;
  }

  public int getId() {
    return this.id;
  }

}
