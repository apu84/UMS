package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.TaskStatus;

public interface MutableTaskStatus extends TaskStatus, Mutable, MutableIdentifier<String>,
    MutableLastModifier {
  void setTaskName(String pTaskName);

  void setStatus(Status pStatus);

  void setProgressDescription(String pProgressDescription);
}
