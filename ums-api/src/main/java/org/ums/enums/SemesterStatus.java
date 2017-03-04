package org.ums.enums;

/**
 * Created by Ifti on 24-Mar-16.
 */
public enum SemesterStatus {
  INACTIVE(0, "Inactive"),
  ACTIVE(1, "Active"),
  NEWLY_CREATED(2, "Newly Created"),
  FETCH_ALL(3, "Fetch All Semesters"),
  FETCH_ALL_WITH_NEWLY_CREATED(4, "Fetch All Semesters along with Newly created semester");

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
