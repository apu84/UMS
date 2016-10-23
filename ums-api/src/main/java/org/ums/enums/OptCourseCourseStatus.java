package org.ums.enums;

/**
 * Created by Ifti on 17-Mar-16.
 */
public enum OptCourseCourseStatus {
  APPLIED(0, "Applied"), APPROVED(1, "Approved"), REJECTED(2, "Rejected"), REJECTED_SHIFTED(3,
      "Rejected and Shifted");

  private String label;
  private int id;

  private OptCourseCourseStatus(int id, String label) {
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
