package org.ums.enums;

/**
 * Created by Monjur-E-Morshed on 03-Dec-16.
 */
public enum FacultyType {
  Engineering(0, "Engineering Faculty"),
  Business(1, "Business Faculty");

  private String label;
  private int id;

  private FacultyType(int id, String label) {
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
