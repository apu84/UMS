package org.ums.manager;

import org.ums.domain.model.immutable.Examiner;
import org.ums.domain.model.mutable.MutableExaminer;

public interface ExaminerManager extends AssignedTeacherManager<Examiner, MutableExaminer, Long> {
}
