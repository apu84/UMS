package org.ums.manager;

import org.ums.domain.model.immutable.TaskStatus;
import org.ums.domain.model.mutable.MutableTaskStatus;

public interface TaskStatusManager extends ContentManager<TaskStatus, MutableTaskStatus, String> {
}
