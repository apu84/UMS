package org.ums.fee.payment;

import java.io.Serializable;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.Student;

public interface StudentPayment extends Serializable, EditType<MutableStudentPayment>, LastModifier, Identifier<Long> {

  String getTransactionId();

  Semester getSemester();

  Integer getSemesterId();

  Student getStudent();

  String getStudentId();

  Double getAmount();

  Status getStatus();

  Date getAppliedOn();

  Date getVerifiedOn();

  enum Status {
    RECEIVED(1),
    REJECTED(2),
    APPLIED(0);

    private static final Map<Integer, Status> lookup = new HashMap<>();

    static {
      for(Status c : EnumSet.allOf(Status.class))
        lookup.put(c.getValue(), c);
    }

    private int typeCode;

    Status(int pTypeCode) {
      this.typeCode = pTypeCode;
    }

    public static Status get(final int pTypeCode) {
      return lookup.get(pTypeCode);
    }

    public int getValue() {
      return this.typeCode;
    }
  }
}