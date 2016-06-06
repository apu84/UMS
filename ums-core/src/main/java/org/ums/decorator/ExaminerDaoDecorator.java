package org.ums.decorator;

import org.ums.domain.model.immutable.Examiner;
import org.ums.domain.model.mutable.MutableExaminer;
import org.ums.manager.AssignedTeacherManager;

public class ExaminerDaoDecorator
    extends AssignedTeacherDaoDecorator<Examiner, MutableExaminer, Integer, AssignedTeacherManager<Examiner, MutableExaminer, Integer>> {
}
