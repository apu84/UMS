package org.ums.fee.accounts;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public interface PaymentStatus extends Serializable, EditType<MutablePaymentStatus>, LastModifier, Identifier<Long> {

  String getAccount();

  String getTransactionId();

  PaymentMethod getMethodOfPayment();

  Status getStatus();

  Date getReceivedOn();

  Date getCompletedOn();

  BigDecimal getAmount();

  String getPaymentDetails();

  String getReceiptNo();

  enum PaymentMethod {
    CASH(1, "CASH"),
    PAYORDER(2, "PAYORDER"),
    CHEQUE(3, "CHEQUE");

    private String label;
    private int id;

    PaymentMethod(int id, String label) {
      this.id = id;
      this.label = label;
    }

    private static final Map<Integer, PaymentMethod> lookup = new HashMap<>();

    static {
      for(PaymentMethod c : EnumSet.allOf(PaymentMethod.class)) {
        lookup.put(c.getId(), c);
      }
    }

    public static PaymentMethod get(final int pId) {
      return lookup.get(pId);
    }

    public String getLabel() {
      return this.label;
    }

    public int getId() {
      return this.id;
    }
  }

  enum Status {
    VERIFIED(1, "Received & Verified"),
    REJECTED(2, "Rejected"),
    RECEIVED(0, "Received");

    private String label;
    private int id;

    Status(int id, String label) {
      this.id = id;
      this.label = label;
    }

    private static final Map<Integer, Status> lookup = new HashMap<>();

    static {
      for(Status c : EnumSet.allOf(Status.class)) {
        lookup.put(c.getId(), c);
      }
    }

    public static Status get(final int pId) {
      return lookup.get(pId);
    }

    public String getLabel() {
      return this.label;
    }

    public int getId() {
      return this.id;
    }
  }
}
