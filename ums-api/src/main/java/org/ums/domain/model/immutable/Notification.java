package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableNotification;

import java.io.Serializable;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public interface Notification extends Serializable, EditType<MutableNotification>, LastModifier, Identifier<String> {
  String getProducerId();

  String getConsumerId();

  String getNotificationType();

  String getPayload();

  Date getProducedOn();

  Date getConsumedOn();

  enum Type {
    COURSE_MATERIAL("CM"),
    COURSE_ASSIGNMENT("CA"),
    GRADE_SUBMISSION("GS");

    private static final Map<String, Type> lookup = new HashMap<>();

    static {
      for (Type c : EnumSet.allOf(Type.class))
        lookup.put(c.getValue(), c);
    }

    private String typeCode;

    private Type(String pTypeCode) {
      this.typeCode = pTypeCode;
    }

    public static Type get(final int pTypeCode) {
      return lookup.get(pTypeCode);
    }

    public String getValue() {
      return this.typeCode;
    }
  }
}
