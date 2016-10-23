package org.ums.enums;

public enum OptCourseApplicationStatus {
  APPLIED(0, "Saved"), APPROVED(1, "Submitted");

  private String label;
  private int id;

  private OptCourseApplicationStatus(int id, String label) {
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
