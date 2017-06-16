package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableEmploymentType;

import java.io.Serializable;

public interface EmploymentType extends Serializable, EditType<MutableEmploymentType>, Identifier<Integer>,
    LastModifier {
  String getType();
}
