package org.ums.domain.model.immutable.common;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.common.MutableBloodGroup;

import java.io.Serializable;

public interface BloodGroup extends Serializable, LastModifier, EditType<MutableBloodGroup>, Identifier<Integer> {

  String getBloodGroup();
}
