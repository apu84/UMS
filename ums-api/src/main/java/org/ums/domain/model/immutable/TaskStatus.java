package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableTaskStatus;

import java.io.Serializable;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public interface TaskStatus extends Serializable, EditType<MutableTaskStatus>, Identifier<String>, LastModifier {
  Status getStatus();

  String getProgressDescription();

  Date getTaskCompletionDate();

  enum Status {
    INPROGRESS(1, "Inprogress"),
    COMPLETED(2, "Completed"),
    FAILED(3, "Failed"),
    NONE(0, "");

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
