package org.ums.usermanagement.application;

import java.io.Serializable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;

public interface Application extends Serializable, EditType<MutableApplication>, LastModifier, Identifier<Long> {
  String getName();

  String getDescription();
}
