package org.ums.enums;

/**
 * Created by Monjur-E-Morshed on 03-Dec-16.
 */
public enum FacultyType {
  Engineering(1, "Engineering Faculty"),
  Business(2, "Business Faculty & Social Science"),
  Architecture(3, "Architecture Faculty");

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
