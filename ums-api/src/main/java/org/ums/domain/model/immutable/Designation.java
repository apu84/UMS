package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableDesignation;

import java.io.Serializable;

public interface Designation extends Serializable, EditType<MutableDesignation>, LastModifier, Identifier<Integer> {
  String getDesignationName();

  String getEmployeeType();
}
