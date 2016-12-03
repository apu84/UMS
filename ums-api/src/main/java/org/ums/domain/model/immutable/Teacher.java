package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableTeacher;

import java.io.Serializable;

public interface Teacher extends Serializable, EditType<MutableTeacher>, LastModifier,
    Identifier<String> {
  String getName();

  String getDesignationId();

  String getDesignationName();

  String getDepartmentId();

  Department getDepartment();

  String getDepartmentName();

  boolean getStatus();
}
