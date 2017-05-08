package org.ums.fee.latefee;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.immutable.Semester;

public interface UGLateFee extends Serializable, EditType<MutableUGLateFee>, LastModifier, Identifier<Long> {

  Date getFrom();

  Date getTo();

  BigDecimal getFee();

  Semester getSemester();

  Integer getSemesterId();

  AdmissionType getAdmissionType();

  enum AdmissionType {
    REGULAR_ADMISSION(1, "regular_admission"),
    REGULAR_FIRST_INSTALLMENT(2, "regular_first_installment"),
    REGULAR_SECOND_INSTALLMENT(3, "regular_second_installment"),
    READMISSION(4, "readmission"),
    READMISSION_FIRST_INSTALLMENT(5, "readmission_first_installment"),
    READMISSION_SECOND_INSTALLMENT(6, "readmission_second_installment"),
    NONE(7, "");

    private String label;
    private int id;

    AdmissionType(int id, String label) {
      this.id = id;
      this.label = label;
    }

    private static final Map<Integer, AdmissionType> lookup = new HashMap<>();

    static {
      for(AdmissionType c : EnumSet.allOf(AdmissionType.class)) {
        lookup.put(c.getId(), c);
      }
    }

    public static AdmissionType get(final int pId) {
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
