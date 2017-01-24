package org.ums.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Monjur-E-Morshed on 24-Jan-17.
 */
public enum PaymentMode {
  CASH(1,"payment in cash"),
  DEMAND_NOTE(2,"cash on demand note"),
  PAY_ORDER(3,"cash in pay order");

  private String label;
  private int id;

  PaymentMode(String pLabel, int pId) {
    label = pLabel;
    id = pId;
  }

  private static final Map<Integer, PaymentMode> lookup = new HashMap<>();

  static{
    for(PaymentMode c: EnumSet.allOf(PaymentMode.class)){
      lookup.put(c.getId(), c);
    }
  }

  public static PaymentMode get(final int pId){
    return lookup.get(pId);
  }

  public String getLabel() {
    return label;
  }

  public int getId() {
    return id;
  }
}
