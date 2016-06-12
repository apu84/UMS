package org.ums.domain.model.immutable;

import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.enums.ExamType;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public interface UGBaseRegistration extends Serializable, Identifier<Integer>, LastModifier {
  String getCourseId();

  Course getCourse() throws Exception;

  Integer getSemesterId();

  Semester getSemester() throws Exception;

  String getStudentId();

  Student getStudent() throws Exception;

  String getGradeLetter();

  ExamType getExamType();

  Status getStatus();

  enum Status {
    UNKNOWN(0);
    private static final Map<Integer, Status> lookup
        = new HashMap<>();

    static {
      for (Status c : EnumSet.allOf(Status.class)) {
        lookup.put(c.getValue(), c);
      }
    }

    private Integer statusCode;

    private Status(final Integer pStatusCode) {
      this.statusCode = pStatusCode;
    }

    public static Status get(final Integer pStatusCode) {
      return lookup.get(pStatusCode);
    }

    public Integer getValue() {
      return this.statusCode;
    }
  }
}
