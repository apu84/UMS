package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableSemesterEnrollment;

import java.io.Serializable;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public interface SemesterEnrollment extends Serializable, LastModifier, Identifier<Integer>, EditType<MutableSemesterEnrollment> {
  Integer getSemesterId();

  Semester getSemester() throws Exception;

  Integer getProgramId();

  Program getProgram() throws Exception;

  Integer getYear();

  Integer getAcademicSemester();

  Date getEnrollmentDate();

  Type getType();

  enum Type {
    TEMPORARY(0),
    PERMANENT(1);

    private static final Map<Integer, Type> lookup
        = new HashMap<>();

    static {
      for (Type c : EnumSet.allOf(Type.class)) {
        lookup.put(c.getValue(), c);
      }
    }

    private Integer typeCode;

    private Type(final Integer pTypeCode) {
      this.typeCode = pTypeCode;
    }

    public static Type get(final Integer pTypeCode) {
      return lookup.get(pTypeCode);
    }

    public Integer getValue() {
      return this.typeCode;
    }
  }
}
