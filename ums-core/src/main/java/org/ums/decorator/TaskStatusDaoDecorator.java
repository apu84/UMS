package org.ums.decorator;

import org.ums.domain.model.immutable.TaskStatus;
import org.ums.domain.model.mutable.MutableTaskStatus;
import org.ums.manager.TaskStatusManager;

public class TaskStatusDaoDecorator extends
    ContentDaoDecorator<TaskStatus, MutableTaskStatus, String, TaskStatusManager> implements
    TaskStatusManager {

}
