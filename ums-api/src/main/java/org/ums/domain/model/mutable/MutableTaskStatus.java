package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.TaskStatus;

import java.util.Date;

public interface MutableTaskStatus extends TaskStatus, Editable<String>, MutableIdentifier<String>,
    MutableLastModifier {
  void setStatus(Status pStatus);

  void setProgressDescription(String pProgressDescription);

  void setTaskCompletionDate(Date pDate);
}
