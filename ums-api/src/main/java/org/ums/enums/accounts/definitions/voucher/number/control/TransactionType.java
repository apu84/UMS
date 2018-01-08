package org.ums.enums.accounts.definitions.voucher.number.control;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Monjur-E-Morshed on 07-Jan-18.
 */
public enum TransactionType {

  JOURNAL_VOUCHER("JV", "Journal Voucher"),
  CREDIT_NOTE("CN", "Credit Note"),
  DEBIT_NOTE("DN", "Debit Note"),
  CASH_PAYMENT("CP", "Cash Payment"),
  CASH_RECEIPT("CR", "Cash Receipt"),
  BANK_PAYMENT("BP", "Bank Payment"),
  BANK_RECEIPT("BR", "Bank Receipt"),
  CONTRA_VOUCHER("CV", "Contra Voucher"),
  SALES_INVOICE("SI", "Sales Invoice"),
  PURCHASE_BILL("PB", "Purchase Bill"),
  TR_6("TR", "TR6");

  private String label;
  private String id;

  private TransactionType(String id, String label) {
    this.id = id;
    this.label = label;
  }

  private static final Map<String, TransactionType> lookup = new HashMap<>();

  static {
    for(TransactionType c : EnumSet.allOf(TransactionType.class)) {
      lookup.put(c.getId(), c);
    }
  }

  public static TransactionType get(final String pId) {
    return lookup.get(pId);
  }

  public String getLabel() {
    return this.label;
  }

  @JsonValue
  public String getId() {
    return this.id;
  }
}
