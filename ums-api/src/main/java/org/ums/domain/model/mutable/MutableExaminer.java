package org.ums.domain.model.mutable;

import org.ums.domain.model.immutable.Examiner;
import org.ums.domain.model.immutable.Teacher;

public interface MutableExaminer extends Examiner, MutableAssignedTeacher {
  void setPreparerId(final String pTeacherId);

  void setPreparer(final Teacher pTeacher);

  void setScrutinizer(final Teacher pTeacherId);

  void setScrutinizerId(final String pTeacherId);
}
