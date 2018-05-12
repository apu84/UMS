package org.ums.enums.accounts.general.ledger.vouchers;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Monjur-E-Morshed on 08-Feb-18.
 */
public enum AccountTransactionType {
  BUYING("B"),
  SELLING("S"),
  OPENING_BALANCE("O");

  private static final Map<String, AccountTransactionType> Lookup = new HashMap<>();

  static {
    for(AccountTransactionType c : EnumSet.allOf(AccountTransactionType.class))
      Lookup.put(c.getValue(), c);
  }

  private String typeValue;

  AccountTransactionType(String pTypeValue) {
    typeValue = pTypeValue;
  }

  public static AccountTransactionType get(final String pTypeValue) {
    return Lookup.get(pTypeValue);
  }

  public String getValue() {
    return this.typeValue;
  }
}
