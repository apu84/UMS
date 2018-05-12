package org.ums.enums.accounts.definitions.account.balance;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum AccountType {
  OPENING_BALANCE_ADJUSTMENT_ACCOUNT(1111199999L);
  private static final Map<Long, AccountType> Lookup = new HashMap<>();

  static {
    for(AccountType c : EnumSet.allOf(AccountType.class))
      Lookup.put(c.getValue(), c);
  }

  private Long typeValue;

  AccountType(Long pTypeValue) {
    typeValue = pTypeValue;
  }

  @JsonValue
  public static AccountType get(final Long pTypeValue) {
    return Lookup.get(pTypeValue);
  }

  public Long getValue() {
    return this.typeValue;
  }
}