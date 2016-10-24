package org.ums.enums;

/**
 * Created by Ifti on 19-Mar-16.
 */
public enum OptApplicationType {
  STUDENT_APPLIED(0, "Student Applied"),
  TEACHER_APPLIED(1, "Teacher Applied");

  private String label;
  private int id;

  private OptApplicationType(int id, String label) {
    this.id = id;
    this.label = label;
  }

  public String getLabel() {
    return label;
  }

  public int getId() {
    return id;
  }

}
