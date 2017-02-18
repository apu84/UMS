package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableSemesterWithdrawal;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public interface SemesterWithdrawal extends Serializable, LastModifier,
    EditType<MutableSemesterWithdrawal>, Identifier<Long> {
  Semester getSemester();

  Student getStudent();

  Program getProgram();

  String getCause();

  String getAppDate();

  int getStatus();

  String getComment();
}
