package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.mutable.MutableExaminer;

public interface Examiner extends AssignedTeacher, EditType<MutableExaminer> {
  String getPreparerId();

  Teacher getPreparer() throws Exception;

  String getScrutinizerId();

  Teacher getScrutinizer() throws Exception;

}
