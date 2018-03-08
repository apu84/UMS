package org.ums.enums.accounts.definitions.voucher.number.control;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Monjur-E-Morshed on 08-Mar-18.
 */
public enum VoucherType {
  JOURNAL_VOUCHER(1L, "Journal Voucher"),
  CREDIT_NOTE(2L, "Credit Note"),
  DEBIT_NOTE(3L, "Debit Note"),
  CASH_PAYMENT(4L, "Cash Payment"),
  CASH_RECEIPT(5L, "Cash Receipt"),
  BANK_PAYMENT(6L, "Bank Payment"),
  BANK_RECEIPT(7L, "Bank Receipt"),
  CONTRA_VOUCHER(8L, "Contra Voucher"),
  SALES_INVOICE(9L, "Sales Invoice"),
  PURCHASE_BILL(10L, "Purchase Bill");

  private String label;
  private Long id;

  private VoucherType(Long id, String label) {
    this.id = id;
    this.label = label;
  }

  private static final Map<Long, VoucherType> lookup = new HashMap<>();

  static {
    for(VoucherType c : EnumSet.allOf(VoucherType.class)) {
      lookup.put(c.getId(), c);
    }
  }

  public static VoucherType get(final Long pId) {
    return lookup.get(pId);
  }

  public String getLabel() {
    return this.label;
  }

  @JsonValue
  public Long getId() {
    return this.id;
  }
}
