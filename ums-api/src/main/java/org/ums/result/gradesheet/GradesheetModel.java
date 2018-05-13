package org.ums.result.gradesheet;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.immutable.Student;
import org.ums.domain.model.immutable.StudentRecord;
import org.ums.domain.model.immutable.UGRegistrationResult;

import java.util.List;

public interface GradesheetModel extends EditType<MutableGradesheetModel>, Identifier<Long> {
  Student getStudent();

  StudentRecord getStudentRecord();

  List<UGRegistrationResult> getSemesterResults();

  List<UGRegistrationResult> getAllResults();
}
