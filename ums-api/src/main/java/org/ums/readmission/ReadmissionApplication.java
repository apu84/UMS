package org.ums.readmission;

import java.io.Serializable;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.Student;

public interface ReadmissionApplication extends Serializable, EditType<MutableReadmissionApplication>, LastModifier,
    Identifier<Long> {

  Date getAppliedOn();

  Semester getSemester();

  Integer getSemesterId();

  Student getStudent();

  String getStudentId();

  Course getCourse();

  String getCourseId();

  Status getApplicationStatus();

  enum Status {
    REJECTED(0, "Rejected"),
    APPROVED(1, "Approved"),
    APPLIED(2, "Applied");

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
