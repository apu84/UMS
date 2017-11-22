package org.ums.fee;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public interface FeeCategory extends Serializable, EditType<MutableFeeCategory>, LastModifier, Identifier<String> {

  String getFeeId();

  FeeType getType();

  Integer getFeeTypeId();

  String getName();

  String getDescription();

  String getDependencies();

  DeliveryType getDeliveryType();

  enum Categories {
    ADMISSION,
    REGISTRATION,
    ESTABLISHMENT,
    TUITION,
    LABORATORY,
    READMISSION,
    IMPROVEMENT,
    CARRY,
    THEORY_REPEATER,
    SESSIONAL_REPEATER,
    THEORY,
    SESSIONAL,
    INSTALLMENT_CHARGE,
    GRADESHEET_PROVISIONAL,
    GRADESHEET_DUPLICATE,
    PROVISIONAL_CERTIFICATE_INITIAL,
    PROVISIONAL_CERTIFICATE_DUPLICATE,
    TRANSCRIPT_INITIAL,
    TRANSCRIPT_DUPLICATE,
    CERTIFICATE_CONVOCATION,
    CERTIFICATE_DUPLICATE,
    LATE_FEE,
    DROP_PENALTY,
    CERTIFICATE_CHARACTER,
    CERTIFICATE_MIGRATION,
    CERTIFICATE_STUDENTSHIP,
    CERTIFICATE_LANGUAGE_PROFICIENCY,
    TESTIMONIAL_DEPARTMENT;
  }

  enum DeliveryType {
    SINGLE(1, "SINGLE"),
    MULTIPLE(2, "MULTIPLE");

    private String label;
    private int id;

    DeliveryType(int id, String label) {
      this.id = id;
      this.label = label;
    }

    private static final Map<Integer, DeliveryType> lookup = new HashMap<>();

    static {
      for(DeliveryType c : EnumSet.allOf(DeliveryType.class)) {
        lookup.put(c.getId(), c);
      }
    }

    public static DeliveryType get(final int pId) {
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
