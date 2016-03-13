package org.ums.enums;

public enum OptCourseStatus {
  APPLIED(0,"Main-Connection"),
  APPROVED(1,"Sub-Connection"),
  REJECTED(2,"Sub-Connection"),
  REJECTED_SHIFTED(3,"Sub-Connection");

  private String label;
  private int id;

  private OptCourseStatus(int id,String label) {
    this.id=id;
    this.label = label;
  }
  public String getLabel() {
    return label;
  }
  public int getId() {
    return id;
  }

}

