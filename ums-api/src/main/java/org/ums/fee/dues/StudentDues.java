package org.ums.fee.dues;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.immutable.Student;
import org.ums.fee.FeeCategory;
import org.ums.fee.certificate.CertificateStatus;
import org.ums.usermanagement.user.User;

public interface StudentDues extends Serializable, EditType<MutableStudentDues>, LastModifier, Identifier<Long> {

  FeeCategory getFeeCategory();

  String getFeeCategoryId();

  String getDescription();

  String getTransactionId();

  Student getStudent();

  String getStudentId();

  BigDecimal getAmount();

  Date getAddedOn();

  Date getPayBefore();

  User getUser();

  String getUserId();

  Status getStatus();

  enum Status {
    NOT_PAID(0, "NOT_PAID"),
    APPLIED(1, "APPLIED"),
    PAID(2, "PAID");

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
