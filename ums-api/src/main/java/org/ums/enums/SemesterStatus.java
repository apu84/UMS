package org.ums.enums;

/**
 * Created by Ifti on 24-Mar-16.
 */
public enum SemesterStatus {
  INACTIVE(0, "Inactive"), ACTIVE(1, "Active"), NEWLY_CREATED(2, "Newly Created");

  private String label;
  private int id;

  private SemesterStatus(int id, String label) {
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
