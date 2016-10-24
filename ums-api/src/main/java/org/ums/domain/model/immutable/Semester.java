package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableSemester;

import java.io.Serializable;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public interface Semester extends Serializable, EditType<MutableSemester>, LastModifier,
    Identifier<Integer> {

  String getName() throws Exception;

  Date getStartDate() throws Exception;

  Date getEndDate() throws Exception;

  ProgramType getProgramType() throws Exception;

  int getProgramTypeId();

  Status getStatus() throws Exception;

  enum Status {
    ACTIVE(1),
    INACTIVE(0),
    NEWLY_CREATED(2);

    private static final Map<Integer, Status> lookup = new HashMap<>();

    static {
      for(Status c : EnumSet.allOf(Status.class)) {
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
