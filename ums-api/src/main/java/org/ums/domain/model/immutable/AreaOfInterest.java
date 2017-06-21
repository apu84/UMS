package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableAreaOfInterest;

import java.io.Serializable;

public interface AreaOfInterest extends Serializable, EditType<MutableAreaOfInterest>, Identifier<Integer>,
    LastModifier {
  String getAreaOfInterest();
}
