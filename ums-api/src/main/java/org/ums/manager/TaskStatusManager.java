package org.ums.manager;

import org.apache.commons.lang.StringUtils;
import org.ums.domain.model.immutable.TaskStatus;
import org.ums.domain.model.mutable.MutableTaskStatus;

public interface TaskStatusManager extends ContentManager<TaskStatus, MutableTaskStatus, String> {
  default String buildTaskId(Object... args) {
    return StringUtils.join(args, "_");
  }
}
