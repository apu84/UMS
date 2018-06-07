package org.ums.result.gradesheet;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Student;
import org.ums.domain.model.immutable.StudentRecord;
import org.ums.domain.model.immutable.UGRegistrationResult;

import java.util.List;

public interface MutableGradesheetModel extends GradesheetModel, Editable<Long>, MutableIdentifier<Long> {

}
