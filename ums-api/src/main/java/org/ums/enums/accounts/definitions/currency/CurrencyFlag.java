package org.ums.enums.accounts.definitions.currency;

/**
 * Created by Monjur-E-Morshed on 29-Jan-18.
 */
public enum CurrencyFlag {
  BASE_CURRENCY("B"),
  OTHER_CURRENCY("O");

  private final String value;

  CurrencyFlag(String pValue) {
    value = pValue;
  }

  public String getValue() {
    return value;
  }
}
