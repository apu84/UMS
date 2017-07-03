package org.ums.domain.model.immutable.common;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.common.MutableMaritalStatus;

import java.io.Serializable;

public interface MaritalStatus extends Serializable, LastModifier, EditType<MutableMaritalStatus>, Identifier<Integer> {

  String getMaritalStatus();
}
