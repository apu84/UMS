package org.ums.fee.certificate;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.Student;
import org.ums.usermanagement.user.User;
import org.ums.fee.FeeCategory;

import java.io.Serializable;
import java.util.*;

public interface CertificateStatus extends Serializable, EditType<MutableCertificateStatus>, LastModifier,
    Identifier<Long> {

  FeeCategory getFeeCategory();

  String getFeeCategoryId();

  String getTransactionId();

  Student getStudent();

  String getStudentId();

  Semester getSemester();

  Integer getSemesterId();

  Date getProcessedOn();

  Status getStatus();

  User getUser();

  String getUserId();

  enum Status {
    APPLIED(1, "APPLIED"),
    PROCESSED(2, "PROCESSED");

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
