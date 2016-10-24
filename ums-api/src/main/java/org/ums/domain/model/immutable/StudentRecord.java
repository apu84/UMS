package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableStudentRecord;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public interface StudentRecord extends Serializable, Identifier<Integer>, LastModifier,
    EditType<MutableStudentRecord> {
  String getStudentId();

  Student getStudent() throws Exception;

  Integer getSemesterId();

  Semester getSemester() throws Exception;

  Integer getProgramId();

  Program getProgram() throws Exception;

  Integer getYear();

  Integer getAcademicSemester();

  Float getCGPA();

  Float getGPA();

  Type getType();

  Status getStatus();

  enum Type {
    REGULAR("R"),
    READMISSION_REQUIRED("RR"),
    READMITTED("RA"),
    TEMPORARY("T");

    private static final Map<String, Type> lookup = new HashMap<>();

    static {
      for(Type c : EnumSet.allOf(Type.class)) {
        lookup.put(c.getValue(), c);
      }
    }

    private String typeCode;

    private Type(final String pTypeCode) {
      this.typeCode = pTypeCode;
    }

    public static Type get(final String pTypeCode) {
      return lookup.get(pTypeCode);
    }

    public String getValue() {
      return this.typeCode;
    }
  }

  enum Status {
    FAILED("F"),
    PASSED("P"),
    UNKNOWN("U"),
    WITHHELD("W");

    private static final Map<String, Status> lookup = new HashMap<>();

    static {
      for(Status c : EnumSet.allOf(Status.class)) {
        lookup.put(c.getValue(), c);
      }
    }

    private String statusCode;

    private Status(final String pStatusCode) {
      this.statusCode = pStatusCode;
    }

    public static Status get(final String pStatusCode) {
      return lookup.get(pStatusCode);
    }

    public String getValue() {
      return this.statusCode;
    }
  }
}
