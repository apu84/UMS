package org.ums.enums.registrar;

public enum PublicationStatus {
  WAITING(0, "0"),
  ACCEPTED(1, "1"),
  REJECTED(2, "2");

  private String label;
  private int id;

  private PublicationStatus(int id, String label) {
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
