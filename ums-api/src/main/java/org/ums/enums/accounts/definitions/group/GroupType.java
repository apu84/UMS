package org.ums.enums.accounts.definitions.group;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Monjur-E-Morshed on 11-Mar-18.
 */
public enum GroupType {
  ASSETS("1"),
  LIABILITIES("2"),
  INCOME("3"),
  EXPENSES("4"),
  BANK_ACCOUNTS("5"),
  CASH_IN_HAND("6"),
  SUNDRY_DEBTOR("7"),
  SUNDRY_CREDITOR("8");

  private static final Map<String, GroupType> Lookup = new HashMap<>();

  static {
    for(GroupType c : EnumSet.allOf(GroupType.class))
      Lookup.put(c.getValue(), c);
  }

  private String typeValue;

  GroupType(String pTypeValue) {
    typeValue = pTypeValue;
  }

  public static GroupType get(final String pTypeValue) {
    return Lookup.get(pTypeValue);
  }

  @JsonValue
  public String getValue() {
    return this.typeValue;
  }
}
