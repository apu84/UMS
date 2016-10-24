package org.ums.enums;

/**
 * Created by Ifti on 03-Jun-16.
 */
public enum RecheckStatus {
  RECHECK_FALSE(0, "Recheck False"),
  RECHECK_TRUE(1, "Recheck True");

  private String label;
  private int id;

  private RecheckStatus(int id, String label) {
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
