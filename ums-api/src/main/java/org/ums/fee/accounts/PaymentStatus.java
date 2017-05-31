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

  boolean isPaymentComplete();

  Date getReceivedOn();

  Date getCompletedOn();

  BigDecimal getAmount();

  String getPaymentDetails();

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
}
