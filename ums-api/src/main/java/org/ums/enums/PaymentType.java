package org.ums.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Monjur-E-Morshed on 24-Jan-17.
 */
public enum PaymentType {
  ADMISSION_FEE(1,"Admission Fee"),
  MIGRATION_FEE(2,"Migration Fee");

  private String label;
  private int id;

  private PaymentType(int id, String label){
    this.id = id;
    this.label = label;
  }

  private static final Map<Integer, PaymentType> lookup = new HashMap<>();

  static{
    for(PaymentType c: EnumSet.allOf(PaymentType.class)){
      lookup.put(c.getId(), c);
    }
  }

  public static PaymentType get(final int pId){
    return lookup.get(pId);
  }

  public String getLabel() {
    return this.label;
  }

  public int getId() {
    return this.id;
  }
}
